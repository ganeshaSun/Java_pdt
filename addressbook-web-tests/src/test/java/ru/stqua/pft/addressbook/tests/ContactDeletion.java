package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletion extends TestBase{


  @Test
  public void testContactDeletion() throws Exception {
    app.getContactHelper().returnToContactPage();
    app.getContactHelper().contactDeletion();
    app.getContactHelper().returnToContactPage();
  }




}
