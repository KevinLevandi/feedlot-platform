package com.whitesheep.platform.api;

import com.whitesheep.platform.api.Role;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-18T22:18:28")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Date> createdAt;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Date> tstamp;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, Date> updatedAt;

}