Our algorithm starts with 1-median. Then it adds facilities one by one until it reaches p-facilities.
While adding facilities, algorithm checks all potential locations to add 1 more facility. 
In order to check potential locations, algorithm uses findMinIndex method. Besides, it checks demands and supplies.
However, in first iterations it doesn't check demand-supply because in some cases, there is no enough supply for all points.


We started increasing p, number of facilities and number of points. 
As we increase three of them linearly(with same ratio), execution time incrased by their multipication(cube).
We also check the increase in the execution time when we increase 3 parameters independently.
Each of them affected time linearly.


You can see our experiment results below;
--------------------------------------
index	p    facility	point	Time(s)
1	2	5	20	0.03
2	4	10	40	0.09
3	6	15	60	0.2
4	8	20	80	0.35
5	10	25	100	0.5
6	12	30	120	0.9
7	14	35	140	1.6
8	16	40	160	2.5
9	18	45	180	4.1
10	20	50	200	6
---------------------------------------
increasing p, Linear
p    facility	point	Time(s)
5	50	100	0.8
10	50	100	1.6
15	50	100	2.3
20	50	100	2.7
25	50	100	3.5
30	50	100	4
---------------------------------------
increasing # of facilities, Linear
p    facility	point	Time(s)
7	10	100	0.2
7	20	100	0.4
7	30	100	0.55
7	40	100	0.8
7	50	100	1
7	60	100	1.3
---------------------------------------
increasing # of points, Linear
p    facility	point	Time(s)
10	20	20	0.18
10	20	40	0.32
10	20	60	0.4
10	20	80	0.58
10	20	100	0.65
---------------------------------------


