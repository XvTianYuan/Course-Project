# 				Database project report

##### Team member: 孙挺，徐天元，施建平，陈林尧

### (1) system requirement analysis and definition

This a reservation system. Users can reserve a stadium or equipment or instructor for a period of time. 

1、Users need to know the free time of the stadium or equipment or instructor they wanted.  

2、If a user is not sure which stadium he wants, he can choose a location and a period of time, and know  all the  free stadium he can reserve at that period.

3、Moreover, for the equipment, users need to know how many equipment can be rented from the stadium they selected.  

4、If some users don’t return the equipment on time, the manager of stadium should limit this user’s  power to reserve. So we need to add a attribute called “credits” to user.

5、If the manager of stadium  finds that some equipment are broken that they can’t be used any more. Then we  need to cast them. So we need an attribute called “usable” in equipment.

6、We also need some functions provided for the manager of database system. If some instructors leave or some unusable equipment need to be deleted, the database system manager should delete these data easily.

7、If some new users sign in, or some new instructors come, or the manager of stadium have bought some new equipment, the database system manager should add new data easily.



### (2)Data requirement analysis and definition

The system ask you to insert the data of the user,the sports fields,the sport courses,the sports instructors and their skill,the type of sports equipment ,each equipment and the company that provide the sports equipment .

We set all the people that connect to the database in a table called user .Each people that add in the table would have their user name and password for logging .Also they have a credit .If the user do some things that disobey the rule ,his credit would be cut down and would influence his authority to do some operations .

The user that log in the system can have the power to search for the specific sports field ,sports instructors and equipment in the specific time and make appointment for them if they are free .We use the table reservation to save the appointment message and the user can do the query and making appointment easily by search the table .

When we get new user in the school ,we can insert his message ,include username and password into the user table .If he is a teacher ,we would also insert his message to the instructor table and connect to the skill.If he would teach courses ,we also add the correspond course message to the course table .

For each gym ,it has its type and location .User can search a gym in many ways ,by its type or by its location 

For each equipment ,we make a table to record the type information of it and a table of the equipment provider information .As for the equipment item ,it has a property "useable" that denote whether it is stable to be used by the user .If the item cannot be used ,the database administrator would repair it or discard it and do the correspond operation in the table .Also ,the belong gym is a property of it.If the user would not return the equipment to the belonged gym on time ,his credit would be influenced .



### （3）ER-digram

![1559960361043](C:\Users\labman028\AppData\Roaming\Typora\typora-user-images\1559960361043.png)