package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTest extends TestBase{


  @Test
  public void testContactDeletion() throws Exception {
    app.getContactHelper().goToContactPage();
    app.getContactHelper().contactDeletion();
    app.getContactHelper().goToContactPage();
  }




}
