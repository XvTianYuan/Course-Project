# ReadMe

## Design strategy

### Topological Graph

  To store the virtual network structure in mininet as the topology which can be run Dijikastra
algorithm on, we use the adjacency list to record the information of all switches, and the
dictionaries to record the links of each switch and the corresponding ports. Additionally, we
implement the function of changing the topology structure accordingly when adding or deleting a
switch or link in mininet .  



### Forwarding table  

  After the topology of all switches and links being set up, we apply the Dijikastra algorithm to
every switch and get D(v) dictionary which stores the length of shortest path from any other
switches to the source and P(v) dictionary which stores the precursor switch of any other
switches along the shortest path to the source. According to P(v), we can get the forwarding table
of every switch which stores the destination switch and the corresponding output port.  

### flow table  

  In order to store the result of Dijikastra algorithm, l use a dictionary whose value is another
dictionary. The key of the first dictionary is the mac address of the packet destination. The key of
the second dictionary is the switch.dp.id, the value is port id.  



### ARP reply  

```python
send_arp(arp_opcode=2, vlan_id=VLANID_NONE, dst_mac=arp_msg.src_mac,sender_mac=arp_table[arp_msg.dst_ip], sender_ip=arp_msg.dst_ip,target_ip=arp_msg.src_ip,target_mac=arp_msg.src_mac,src_port=ofctl.dp.ofproto.OFPP_CONTROLLER, output_port=in_port)
```

usage: when a host need to ping another host by ip but doesn’t know the mac address of it, it will
send a arp request to the switch. The switch will send a arp reply.
parameter illustration:
arp_opcode=2----ARP REPLY
send_ip, send_mac, target_ip, target_mac----the address of ARP reply‘s sender and reciver
src_port, output_port ----the port number of switch  