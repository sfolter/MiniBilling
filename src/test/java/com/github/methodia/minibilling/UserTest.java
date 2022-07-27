package com.github.methodia.minibilling;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class UserTest {
    User user;
    private String name="Georgi";

    private String referentNumber="1";

    private int numberPricingList=1;

    @Test
    public void testConstructorShouldCreateValidPrices(){
        user=new User(name,referentNumber,numberPricingList);
        Assert.assertEquals("Georgi",user.getName());
        Assert.assertEquals("1",user.getReferentNumber());
        Assert.assertEquals(1,user.getNumberPricingList());
    }
}
