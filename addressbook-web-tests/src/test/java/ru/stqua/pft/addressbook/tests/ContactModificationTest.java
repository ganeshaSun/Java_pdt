package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTest extends TestBase{

    @Test
    public void testContactModification() throws Exception {
      app.getNavigationHelper().goToContactPage();

      if (! app.getContactHelper().isThereAContact()){
        app.getContactHelper().createContact(new ContactData("TestName", "TestMiddleName",
                "test1","TestLastName", "TestNickname", "Title Test",
                "Bookovsky", "196190 Saint-Petersburg, Moskovsky pr. 163",
                "88123456456", "+78945634", "testaddress@mail.ru"));
      }

      List<ContactData> before = app.getContactHelper().getContactList();
      app.getContactHelper().initContactModification();
      ContactData contact = new ContactData("ModifiedContact", "TestMiddleName");
      app.getContactHelper().fillContactForm(contact, false);
      app.getContactHelper().submitContactModification();
      app.getNavigationHelper().goToContactPage();
      List<ContactData> after = app.getContactHelper().getContactList();
      before.remove(0);
      before.add(contact);
      Assert.assertEquals(new HashSet<Object>(after),new HashSet<Object>(before));
    }


  }
