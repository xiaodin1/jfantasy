# `权限设计`

资源 同一资源定义中不能出现重复的Id定义:同时应该避免同一路径反复定义多次的问题
1 get:/users
2 put:/users
3 del:/users
4 post:/users

权限 一个权限可以对应多个资源
1:any
1:ip('') or role('')
1:role('') and ip('')


角色


用户组

系统用户 可以按照角色与用户组进行分配权限


找到资源->找到对应的权限配置->判断权限是否满足

-> 判断角色与用户组