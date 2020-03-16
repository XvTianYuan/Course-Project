"""Example Topology Manager Template
CSCI1680

This class is meant to serve as an example for how you can track the
network's topology from netwokr events.

**You are not required to use this file**: feel free to extend it,
change its structure, or replace it entirely.

"""

from ryu.topology.switches import Port, Switch, Link

MAX = 10000


class Device():
    """Base class to represent an device in the network.

    Any device (switch or host) has a name (used for debugging only)
    and a set of neighbors.
    """

    def __init__(self, name):
        self.name = name

    def __str__(self):
        return "{}({})".format(self.__class__.__name__,
                               self.name)


class TMSwitch(Device):
    """Representation of a switch, extends Device

    This class is a wrapper around the Ryu Switch object,
    which contains information about the switch's ports
    """

    def __init__(self, name, switch):
        super(TMSwitch, self).__init__(name)
        self.neighbors = dict()
        self.switch = switch
        self.ports = switch.ports
        # TODO:  Add more attributes as necessary

    def get_dpid(self):
        """Return switch DPID"""
        return self.switch.dp.id

    def get_ports(self):
        """Return list of Ryu port objects for this switch
        """
        return self.switch.ports

    def get_dp(self):
        """Return switch datapath object"""
        return self.switch.dp

    def add_neighbors(self, src_port, dsr_id, dst_port):
        self.neighbors[src_port] = (dsr_id, dst_port, 0)

    def delete_neighbors(self, src_port):
        self.neighbors[src_port] = None

    def get_neighbors(self):
        return self.neighbors


class TMHost(Device):
    """Representation of a host, extends Device

    This class is a wrapper around the Ryu Host object,
    which contains information about the switch port to which
    the host is connected
    """

    def __init__(self, name, host):
        super(TMHost, self).__init__(host)

        self.host = host
        # TODO:  Add more attributes as necessary

    def get_mac(self):
        return self.host.mac

    def get_ips(self):
        return self.host.ipv4

    def get_port(self):
        """Return Ryu port object for this host"""
        return self.host.port

    # . . .


class TopoManager():
    """
    Example class for keeping track of the network topology

    """

    def __init__(self):
        # TODO:  Initialize some data structures
        self.all_devices = []
        self.all_hosts = []
        self.all_switch = dict()
        self.macList = dict()
        self.graph = graph()
        self.arpDict = dict()
        self.forward_table = dict()

    def testFlowTable(self, mac, switch, port):
        if self.macList[mac] == None:
            tmp = dict()
            tmp[switch] = port
            self.macList[mac] = tmp
        else:
            self.macList[mac][switch] = port
        # print("macList{}".format(self.macList))

    def add_ARP(self, mac, ip):
        # print(self.arpDict)
        self.arpDict[ip[0]] = mac

    def add_switch(self, sw):
        name = "switch_{}".format(sw.dp.id)
        switch = TMSwitch(name, sw)
        self.all_switch[sw.dp.id] = switch
        # print("add switch {} in control".format(name))
        self.all_devices.append(switch)
        self.graph.add_switch(switch)
        for i in switch.get_ports():
            switch.neighbors[i.port_no] = None

    def delete_switch(self, sw):
        name = "switch_{}".format(sw.dp.id)
        switch = TMSwitch(name, sw)
        del self.all_switch[sw.dp.id]
        # print("delete switch {} in control".format(name))
        self.all_devices.remove(switch)
        self.graph.delete_switch(switch)

    def add_host(self, h):
        name = "host_{}".format(h.mac)
        # print("add host {} in control".format(name))
        host = TMHost(name, h)
        self.all_devices.append(host)
        self.all_hosts.append(host)
        self.macList[h.mac] = None
        # print(self.macList)
        # TODO:  Add host to some data structure(s)
        self.changeMacList()

    def add_link(self, src_id, src_port, dst_id, dst_port):
        src = self.all_switch[src_id]
        self.graph.add_link(src, src_port, dst_id, dst_port)

    def delete_link(self, src_id, src_port):
        src = self.all_switch[src_id]
        self.graph.delete_link(src, src_port)

    def up_port(self, id, port):
        if port == 65534:
            pass
        else:
            s = self.all_switch[id]
            n = s.get_neighbors()
            if n[port] != None:
                port_id = n[port][0]
                port_num = n[port][1]
                new = (port_id, port_num, 0)
                s.neighbors[port] = new

    def down_port(self, id, port):
        if port == 65534:
            pass
        else:
            s = self.all_switch[id]
            n = s.get_neighbors()
            if n[port] != None:
                port_id = n[port][0]
                port_num = n[port][1]
                new = (port_id, port_num, -1)
                s.neighbors[port] = new

    def graph_test(self):
        switch = self.graph.switchs_list
        print("sample: {port_no:(switch,port_no,state)}")
        for s in switch:
            print("switch{}".format(s.get_dpid()))
            print(s.get_neighbors())

    def shortest_path(self):
        # 对每个交换机运行最短路径算法
        for switch in self.graph.switchs_list:
            # 初始化
            fw_tb = dict()
            src = switch.get_dpid()
            # print(src, ":")
            N = []
            N.append(switch)
            D = dict()
            P = dict()
            # 对每个除了起点以外的节点计s算D(v),P(v)
            for v in self.graph.switchs_list:
                if v not in N:
                    n = v.get_neighbors()
                    v_id = v.get_dpid()
                    D[v_id] = MAX
                    P[v_id] = None
                    for i in range(len(n)):
                        if n[i+1] != None:
                            if n[i+1][0] == src:
                                D[v_id] = 1
                                P[v_id] = (i + 1, src, n[i+1][1])
            # 循环找到每个节点到起点的最短路径
            while len(N) < len(self.graph.switchs_list):
                d = MAX
                w = None
                # 找到不在N中的D(v)最小的节点w，加入N中
                for v in self.graph.switchs_list:
                    if v not in N:
                        v_id = v.get_dpid()
                        if D[v_id] < d:
                            d = D[v_id]
                            w = self.all_switch[v_id]
                if w != None:
                    N.append(w)
                    # 寻找不在N中的w的邻居，更新D(v)值
                    n = w.get_neighbors()
                    for i in range(len(n)):
                        if n[i + 1] != None:
                            v = self.all_switch[n[i + 1][0]]
                            if v not in N:
                                v_id = n[i + 1][0]
                                if D[v_id] > D[w.get_dpid()] + 1:
                                    D[v_id] = D[w.get_dpid()] + 1
                                    P[v_id] = (
                                        i + 1, w.get_dpid(), n[i + 1][1])
                else:
                    break
            for s in self.graph.switchs_list:
                s_id = s.get_dpid()
                final_id = s_id
                if s_id != src:
                    if P[s_id] != None:
                        pre = P[s_id][1]
                        while pre != src:
                            s_id = pre
                            pre = P[s_id][1]
                        fw_dst = final_id
                        fw_port = P[s_id][2]
                        fw_tb[fw_dst] = fw_port
            self.forward_table[switch.get_dpid()] = fw_tb
            # print("switch{} fw_tb{}".format(switch.get_dpid(), fw_tb))
        print("forwardTable{}".format(self.forward_table))
        # 改变macList
        # for sw in fw_tb:
        #     for h in self.all_hosts:
        #         if sw == h.get_port().dpid:
        #             self.testFlowTable(
        #                 h.get_mac(), switch.get_dpid(), fw_tb[sw])

    def changeMacList(self):
        for mac in self.macList:
            self.macList[mac] = None
        for switch in self.forward_table:
            for sw in self.forward_table[switch]:
                for h in self.all_hosts:
                    if sw == h.get_port().dpid:
                        self.testFlowTable(
                            h.get_mac(), switch, self.forward_table[switch][sw])
        # print("Changeed macList{}".format(self.macList))
        for mac in self.macList:
            toString = "To "+str(mac)+":\n"
            if self.macList[mac] is not None:
                for switch in self.macList[mac]:
                    toString += "Edge: switch_{}/{}".format(
                        switch, self.macList[mac][switch])
                    # print("all_switch:{}".format(self.all_switch))
                    hasNeighbour = 0
                    for dst in self.all_switch:
                        # print("neighbour:{}".format(
                        #     self.all_switch[switch].neighbors))
                        if self.all_switch[switch].neighbors[self.macList[mac][switch]] is not None and dst == self.all_switch[switch].neighbors[self.macList[mac][switch]][0]:
                            toString += " <---> switch_{}/{}\n".format(
                                dst, self.all_switch[switch].neighbors[self.macList[mac][switch]][1])
                            hasNeighbour = 1
                    if hasNeighbour == 0:
                        toString += "<---> host {}\n".format(mac)
            print(toString)


class graph():
    """
    用字典存每个交换机连接的的邻居以及相应端口号
    """

    def __init__(self):
        self.switchs_list = []
        self.links = {}

    def add_switch(self, s):
        self.switchs_list.append(s)
        # print("Add switch successful!")

    def add_link(self, src, src_port, dst_id, dst_port):
        src.add_neighbors(src_port, dst_id, dst_port)
        # print("Add link successful!")

    def delete_switch(self, s):
        self.switchs_list.remove(s)
        # print("Delete switch successful!")

    def delete_link(self, src, src_port):
        src.delete_neighbors(src_port)
