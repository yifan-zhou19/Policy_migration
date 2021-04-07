
#file = open(r'Table 7(8 8 10 10 10 1 1)/policy.txt' , 'r')
file = open(r'Table 7(50 50 10 10 50 1 4)/policy.txt' , 'r')
#file = open(r'Table 7(100 50 10 10 50 1 4)/policy.txt' , 'r')
line = file.readline() 
RU=[]            
while line: 
    rule=dict()
    rule['expressions']=[{'eu':{},'er':{}}]
    rule['operations']=['access']
    rule['decision']='allow'
 # rule=dict('expressions':[{'eu':{},'er':{}}],'operations':['access'],'decision':'allow'), 
    
    #print(line[:10])
    #print(line, end = '')　
    cons = line.strip().split(',')
    for con in cons:
        c = con.split('=')
        if c[0][0]=='u':
            rule['expressions'][0]['eu'][c[0]]=[('is',c[1])]
        elif c[0][0]=='r':
            #rule['er'][c[0]]=c[1]
            rule['expressions'][0]['er'][c[0]]=[('is',c[1])]

    
    RU.append(rule)

    #break
    line = file.readline() 
print(f'\nNumber of policies:{len(RU)}')
for i in RU:
    print(i)
 
file.close()

#file = open(r'Table 7(8 8 10 10 10 1 1)/UAR.txt' , 'r')
file = open(r'Table 7(50 50 10 10 50 1 4)/UAR.txt' , 'r')
#file = open(r'Table 7(100 50 10 10 50 1 4)/UAR.txt' , 'r')


line = file.readline()
U=[] 
UA=[] 
i=1 
while line: 
    u={}
    #print(line[:10])
    #print(line, end = '')　
    cons = line.strip().split(',')
    for con in cons:
        c = con.split('=')
        if(len(c)==2):
            u[c[0]]=c[1]
            if(c[0] not in UA):
                UA.append(c[0])
    u['uid']='user'+str(i)
    i=i+1

    
    U.append(u)

    #break
    line = file.readline() 
print(f'\nNumber of users:{len(U)}')
for i in U:
    print(i)
 
print(UA)
file.close()

#file = open(r'Table 7(8 8 10 10 10 1 1)/OAR.txt' , 'r')
file = open(r'Table 7(50 50 10 10 50 1 4)/OAR.txt' , 'r')
#file = open(r'Table 7(100 50 10 10 50 1 4)/OAR.txt' , 'r')
line = file.readline()
R=[] 
RA=[]
i=1         
while line: 
    r={}
    #print(line[:10])
    #print(line, end = '')　
    cons = line.strip().split(',')
    for con in cons:
        c = con.split('=')
        if(len(c)==2):
            r[c[0]]=c[1]
            if(c[0] not in RA):
                RA.append(c[0])

    r['rid']='resource'+str(i)
    i=i+1
    R.append(r)

    #break
    line = file.readline() 
print(f'\nNumber of resources:{len(R)}')
for i in R:
    print(i)
 
#print(RA)
file.close()


O=['access']
def dU(user,attribute):
    if attribute in user.keys():
        return user[attribute]

def dR(resource,attribute):
    if attribute in resource.keys():
        return resource[attribute]
ABAC_policy=(U,R,O,UA,RA,dU,dR,RU)

def satisfied(u,r,expressions):
    for expression in expressions:
        eu=expression['eu']
        er=expression['er']
        #for all the keys in eu get the constrina of this key
        #if u does not satisfy this const return false\
        if ( len(eu.keys()) != 0 and u != None):
            for key in eu.keys():
                if(dU(u,key)==None):
                    return False
                for (op,c) in eu[key]:
                #op,c=eu[key]
                #print(op,c)
                    if op == 'is' and dU(u,key) != c :
                        return False

        #for all the keys in er get the constrinat of the key
        if ( len(er.keys()) != 0 and r != None):
            for key in er.keys():
                if (dR(r,key)==None):
                    return False
                for (op,c) in er[key]:
                #print(op,c)
                #op,c=er[key]

                    if op == 'is' and dR(r,key)!=c:

                        return False
    
    return True


def check_ABAC_policy(request,abac_policy):
    u,r,o =request
    _,_,_,_,_,_,_,rules = abac_policy
    for rule in rules:
        if o in rule['operations']:
            if satisfied(u,r,rule['expressions'])==True:
                return True
    return False

# check the ABAC policy 
number_allow=0
number_deny=0
for u in U:
    for r in R:
        for o in O:
            if(check_ABAC_policy((u,r,o),ABAC_policy)==True):
                #print(u,'\n',r)
                number_allow= number_allow+1
            else:
                number_deny =number_deny +1 
                #print(f'{u}\n{r}\n{o}\n \n')

print(f'\nFor ABAC policy:\nNumber of allowed requests:{number_allow}\nNumber of denied requests:{number_deny}')

#print(f'{check_ABAC_policy((U[6],R[7],"readMyScores"),ABAC_university)}\n{U[6]}\n{R[7]}')
#print(f'{check_ABAC_policy((U[3],R[5],"addScore"),ABAC_university)}\n{U[3]}\n{R[5]}')
#print(f'Loss at epo {epoch}: {losses/len(training_data)}')

def get_item_id(item,list):
    id = 0
    while(id<len(list)):
        if item == list[id]:
            return id
        id = id + 1
    return None

from numpy import *
# Calculate the number of user constraints
# Calculate the number of conditional permissions(resource constraint,operation)
UC=[]
Permission=[]
Permission_set=[]
EU=[]
i=0
for rule in RU:
    user_cons=rule['expressions'][0]['eu']
    resource_cons=rule['expressions'][0]['er']
    operations=rule['operations']
    for uc_key in user_cons.keys():
        uc = {uc_key:user_cons[uc_key]}
        if uc not in UC:
            UC.append(uc)
    if (resource_cons,operations) not in Permission:
        Permission.append((resource_cons,operations))
########
    if user_cons not in EU:
        EU.append(user_cons)
        Permission_set.append([(resource_cons,operations)]) 
    elif user_cons in EU:
        if (resource_cons,operations) not in Permission_set[get_item_id(user_cons,EU)]:
            Permission_set[get_item_id(user_cons,EU)].append((resource_cons,operations)) 
            print(f'{get_item_id(user_cons,EU)}     {i}')

    i = i+1
print(f'\npermission_set:{len(Permission_set)}')
#print(len(EU))

########
n_uc=len(UC)
n_p=len(Permission_set)
###n_p=len(Permission)
print(f'Number of user constraints:{n_uc}\nNumber of Permission:{n_p}')
#print(UC)

###########
unique_pset=[]
for i in Permission_set:
    if i not in unique_pset:
        unique_pset.append(i)
#print(len(unique_pset))
############
def cover(ma,mb):# ma cover mb
    if ma.shape[1] != mb.shape[1]:
        return False
    for i in range(ma.shape[1]):
        if mb[0,i]==1 and ma[0,i]==0:
            return False


    return True

# permission-constraint matrix
pc=mat(zeros((n_p,n_uc)))
for rule in RU:
    user_cons=rule['expressions'][0]['eu']
    resource_cons=rule['expressions'][0]['er']
    operations=rule['operations']
    ####p_id=get_item_id((resource_cons,operations),Permission)
    p_id=get_item_id(user_cons,EU)
    for uc_key in user_cons.keys():
        uc = {uc_key:user_cons[uc_key]}
        c_id=get_item_id(uc,UC)
     #### if(c_id==None):
     ####     continue
        pc[p_id,c_id]=1
print(f'\nThe pc matrix is\n {pc}')
####rp=mat(diag(array(ones(n_p))))
n_r = n_p
rp=mat(diag(array(ones(n_p))))



for i in range(n_r):
    for j in range(i+1,n_p):
        if (cover(pc[i,],pc[j,])):
            rp[i,j]=1
        if (cover(pc[j,],pc[i,])):
            rp[j,i]=1

print(f'The rp matrix is\n {rp}')

def rest_of(l,r):
    vector = mat(zeros((1,n_p)))
    for role in l:
        if role != r:
            vector = vector + rp[role,]
    for i in range(n_p):
        if vector[0,i]>0:
            vector[0,i]=1
    return vector


def get_RH_sub(RH,r):
    roles = [r]
    flag = True
    while(flag is True):
        flag = False
        for (ri,rj) in RH:
            if ri in roles and rj not in roles:
                roles.append(rj)
                flag = True
    return roles

def get_RH_sup(RH,r):
    roles = [r]
    flag = True
    while(flag is True):
        flag = False
        for (ri,rj) in RH:
            if rj in roles and ri not in roles:
                roles.append(ri)
                flag = True
    return roles



import copy
# User role assignment 
n_r=n_p
rc=pc
URA = dict()

number_ura=0

for u in U:
    URA[u['uid']]=[]
    uv=mat(ones((1,n_uc)))
    for cid in range(n_uc):
        expressions=[{'eu':UC[cid],'er':{}}]
        if satisfied(u,None,expressions)==False:
            uv[0,cid]=0
    for rid in range(n_r):
        if cover(uv,rc[rid,]):
            URA[u['uid']].append(rid)

 
# generate the role-heirarchy
RH = []
for i in range(0,n_r-1):
    for j in range(i+1,n_r):
        if (cover(pc[i,],pc[j,])):
            RH.append((i,j))
        if (cover(pc[j,],pc[i,])):
            RH.append((j,i))        
print(f'\n\nThe role-hierarchy relationship:    {RH}')
# generate the role-permission assignment
RPA = dict()

for ro in range(n_r):
    RPA['role'+str(ro)]=[]

################3
for pid in range(n_p):
    for o in O:
        for r in R:
            for role_id in range(n_r):
                if rp[role_id,pid]==1:
                     #(er,operations) = Permission[pid]
                     for (er,operations) in Permission_set[pid]:
                         expressions=[{'eu':{},'er':er}]
                         if satisfied(None,r,expressions) and o in operations:
                             if ((r,o) not in RPA['role'+str(role_id)]):
                                 RPA['role'+str(role_id)].append((r,o))
##################
 # for r in R:
    #    for o in O:
     #     for pid in range(n_p):
    #            if rp[ro,pid]==1:
                    ########
    #                for (er,operations) in Permission_set[pid]:
                    #print(er,r)
    #                    expressions=[{'eu':{},'er':er}]
                        #print(satisfied(None,r,expressions))
     #                 if satisfied(None,r,expressions) and o in operations:
     #                     if((r,o) not in RPA['role'+str(ro)]):
     #                         RPA['role'+str(ro)].append((r,o))

#Remove redundant ur
print('\nThe user-role assignment:')
for u in U:
    ura =    URA[u['uid']]    
    for (ri,rj) in RH:
        if ri in ura and rj in ura:
            ura.remove(rj)
    number_ura=number_ura+len(URA[u['uid']] )
    print(f'User {get_item_id(u,U)+1}, role: {ura}')
    #for r in ura:
    #    if (r not in Role_assigned):
    #        Role_assigned.append(r)

# Remove redundant rp
# remove permissions in ri
for (ri,rj) in RH:
 # print(len(RPA['role'+str(rj)]),len(RPA['role'+str(ri)]))
    for p in RPA['role'+str(rj)]:
        if (p    in RPA['role'+str(ri)]):
            #print(f'Wrong rp. {p} not in ri')
            #print(ri,rj)
            RPA['role'+str(ri)].remove(p)


print('\n\nThe role-permission assignment:')
number_rpa=0
for ro in range(n_r):
    p=RPA['role'+str(ro)]
    number_rpa=number_rpa+len(p)
    print(f'Role：{ro}, Number of permissions:{len(p)},    Permissions: {p}')



print(f'The number of user-role assignment:    {number_ura}')

print(f'The number of role-permission assignment:    {number_rpa}')

#active roles
Role_active =[]
# if（r,p) exist u,r' in super set
# if(u,r) exist p,r' in sub set
for u in U:
    ura =    URA[u['uid']] 
    for role in ura:
        role_subset=get_RH_sub(RH,role)
        for role_sub in role_subset:
            # p,r' in sub set
            if len(RPA['role'+str(role_sub)]) >0: 
                if role not in Role_active:
                    Role_active.append(role)

#for every role, check u,r' in super set and p,r' in sub set
for role in range(n_r):
    flag_ur=False
    flag_rp=False
    role_superset=get_RH_sup(RH,role)
    role_subset=get_RH_sub(RH,role)


    for role_super in role_superset:
        # (u,r) exist
        if flag_ur:
            break
        for u in U:
            ura =    URA[u['uid']]
            if role_super in ura:
                flag_ur=True
                break
    
    for role_sub in role_subset:
            if len(RPA['role'+str(role_sub)]) >0: 
                flag_rp=True
                break
    
    if flag_rp and flag_ur and role not in Role_active:
        Role_active.append(role)
print(f'The number of roles assigned:    {len(Role_active)}: {Role_active}')


number_ura_a=0
for u in U:
    ura =    URA[u['uid']]
    if len(ura)==0:
        continue
    for ro in ura:
        if ro in Role_active:
            number_ura_a=number_ura_a+1


print(f'The number of active user-role assignment:    {number_ura_a}')

number_rpa_a=0
for ro in range(n_r):
    p=RPA['role'+str(ro)]
    if ro in Role_active:
        number_rpa_a=number_rpa_a+len(p)



print(f'The number of active role-permission assignment:    {number_rpa_a}')

number_rh_a=0
for (ri,rj) in RH:
    if ri in Role_active and rj in Role_active:
        number_rh_a=number_rh_a+1

print(f'The number of RH:    {len(RH)}')
print(f'The number of active RH:    {number_rh_a}')


def get_roles_RH(u,URA,RH):
    roles=URA[u['uid']]
    role_RH=[]
    for r in roles:
        if(r not in role_RH):
            role_RH.append(r)
        for role in get_RH_sub(RH,r):
            if role not in role_RH:
                role_RH.append(role)
    return role_RH

# check RBAC policy
def check_RBAC_policy(request,RPA,URA,RH):
    u,r,o =request
    role_set=get_roles_RH(u,URA,RH)
    
    #for role_id in URA[u['uid']]:
    for role_id in role_set:
        if (r,o) in RPA['role'+str(role_id)]:
            return True

    return False
# Check the RBAC
# check the RBAC policy 

number_allow=0
number_deny=0
for u in U:
    for r in R:
        for o in O:
            if (check_RBAC_policy((u,r,o),RPA,URA,RH)==True):
                number_allow= number_allow+1
            else:
                number_deny =number_deny +1 
                #print(f'{u}\n{r}\n{o}\n \n')

print(f'For RBAC:\nNumber of allowed requests:{number_allow}\nNumber of denied request:{number_deny}')


# check the RBAC policy 
number_allow=0
number_deny=0
for u in U:
    for r in R:
        for o in O:
            if(check_RBAC_policy((u,r,o),RPA,URA,RH)==True):
                number_allow= number_allow+1
            else:
                number_deny =number_deny +1 
                #print(f'{u}\n{r}\n{o}\n \n')
print(f'For RBAC:\nNumber of allowed requests:{number_allow}\nNumber of denied request:{number_deny}')

# check the ABAC policy 
number_allow=0
number_deny=0
m= []
for u in U:
    l = []
    for r in R:
        for o in O:
            if(check_ABAC_policy((u,r,o),ABAC_policy)==True):
                l.append(1)
                number_allow= number_allow+1
            else:
                number_deny =number_deny +1 
                l.append(0)
                #print(f'{u}\n{r}\n{o}\n \n')
    m.append(l)

print(f'For ABAC:\nNumber of allowed requests:{number_allow}\nNumber of denied request:{number_deny}')
#for i in m:
#    print(i)
#number_allow=0
#number_deny=0

correct=0
for u in U:
 
    for r in R:
        for o in O:
            if (check_ABAC_policy((u,r,o),ABAC_policy)==True and check_RBAC_policy((u,r,o),RPA,URA,RH)==False):
                print(f'TF{u} {r} {o}')
            elif (check_ABAC_policy((u,r,o),ABAC_policy)==False and check_RBAC_policy((u,r,o),RPA,URA,RH)==True):
                print(f'FT{u} {r} {o}')
                #number_allow= number_allow+1
            elif (check_ABAC_policy((u,r,o),ABAC_policy)==True and check_RBAC_policy((u,r,o),RPA,URA,RH)==True):
                correct = correct +1
            elif (check_ABAC_policy((u,r,o),ABAC_policy)==False and check_RBAC_policy((u,r,o),RPA,URA,RH)==False):
                correct = correct +1
                #number_deny =number_deny +1 
                #print(f'{u}\n{r}\n{o}\n \n')

print(f'For number of correct:{correct}')


