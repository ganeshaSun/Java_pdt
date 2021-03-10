package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase{

    @Test
    public void testContactModification() throws Exception {
      app.getContactHelper().initContactModification();
      app.getContactHelper().fillContactForm(new ContactData("ModifiedContact", "TestMiddleName",
              "Mod_TestLastName", "modification", "Title Test",
              "modify:Bookovsky", "modify:196190 Saint-Petersburg, Moskovsky pr. 163",
              "modify:88123456456", "modify:+78945634", "modify:testaddress@mail.ru"));
      app.getContactHelper().submitContactModification();
      app.getContactHelper().returnToContactPage();
    }


  }
