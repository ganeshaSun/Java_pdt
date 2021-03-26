package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToContactPage();
   // List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().createContact(new ContactData("TestName", "TestMiddleName",
            "test1","TestLastName", "TestNickname", "Title Test",
            "Bookovsky", "196190 Saint-Petersburg, Moskovsky pr. 163",
            "88123456456", "+78945634", "testaddress@mail.ru"));
    app.getNavigationHelper().goToContactPage();
   // List <ContactData> after = app.getContactHelper().getContactList();
  //  Assert.assertEquals(after.size(), before.size()+1);
  }
}
