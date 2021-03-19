package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.GroupData;

public class ContactDeletionTest extends TestBase{


  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().goToContactPage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("TestName", "TestMiddleName",
              "test1","TestLastName", "TestNickname", "Title Test",
              "Bookovsky", "196190 Saint-Petersburg, Moskovsky pr. 163",
              "88123456456", "+78945634", "testaddress@mail.ru"));
    }
    app.getContactHelper().contactDeletion();
    app.getNavigationHelper().goToContactPage();
  }




}
