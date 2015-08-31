package com.whitesheep.platform.api;

import com.whitesheep.platform.api.Aim;
import com.whitesheep.platform.api.Animal;
import com.whitesheep.platform.api.Bnature;
import com.whitesheep.platform.api.Confinement;
import com.whitesheep.platform.api.Customer;
import com.whitesheep.platform.api.Dnature;
import com.whitesheep.platform.api.Feed;
import com.whitesheep.platform.api.Flag;
import com.whitesheep.platform.api.Role;
import com.whitesheep.platform.api.Sex;
import com.whitesheep.platform.api.Supplier;
import com.whitesheep.platform.api.Type;
import com.whitesheep.platform.api.Unit;
import com.whitesheep.platform.api.Variant;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-31T01:39:45")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Variant> variantCollection;
    public static volatile CollectionAttribute<User, Dnature> dnatureCollection;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile CollectionAttribute<User, Sex> sexCollection;
    public static volatile CollectionAttribute<User, Confinement> confinementCollection;
    public static volatile CollectionAttribute<User, Flag> flagCollection;
    public static volatile CollectionAttribute<User, Aim> aimCollection;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile CollectionAttribute<User, Unit> unitCollection;
    public static volatile CollectionAttribute<User, Bnature> bnatureCollection;
    public static volatile SingularAttribute<User, Date> createdAt;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Date> tstamp;
    public static volatile CollectionAttribute<User, Supplier> supplierCollection;
    public static volatile CollectionAttribute<User, Customer> customerCollection;
    public static volatile CollectionAttribute<User, Animal> animalCollection;
    public static volatile CollectionAttribute<User, Type> typeCollection;
    public static volatile CollectionAttribute<User, Feed> feedCollection;
    public static volatile SingularAttribute<User, Date> updatedAt;

}