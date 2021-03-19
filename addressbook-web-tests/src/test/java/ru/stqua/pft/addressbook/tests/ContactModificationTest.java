package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase{

    @Test
    public void testContactModification() throws Exception {
      if (! app.getContactHelper().isThereAContact()){
        app.getContactHelper().createContact(new ContactData("TestName", "TestMiddleName",
                "test1","TestLastName", "TestNickname", "Title Test",
                "Bookovsky", "196190 Saint-Petersburg, Moskovsky pr. 163",
                "88123456456", "+78945634", "testaddress@mail.ru"));
      }
      app.getContactHelper().initContactModification();
      app.getContactHelper().fillContactForm(new ContactData("ModifiedContact", "TestMiddleName",
              null,"Mod_TestLastName", "modification", "Title Test",
              "modify:Bookovsky", "modify:196190 Saint-Petersburg, Moskovsky pr. 163",
              "modify:88123456456", "modify:+78945634", "modify:testaddress@mail.ru"), false);
      app.getContactHelper().submitContactModification();
      app.getNavigationHelper().returnToContactPage();
    }


  }
