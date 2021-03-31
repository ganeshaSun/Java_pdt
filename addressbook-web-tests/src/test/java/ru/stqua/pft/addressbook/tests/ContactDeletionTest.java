package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;

import java.util.List;
import java.util.Set;

public class ContactDeletionTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().contactPage();
    if (app.contact().all().size()==0){
      app.contact().create(new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
              .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
              .withTel("88123456456").withMobile("+78945634").withEmail("testaddress@mail.ru"));
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().returnToContactPage();
    Set<ContactData> after = app.contact().all();

    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(deletedContact);
    Assert.assertEquals(after, before);
  }




}
