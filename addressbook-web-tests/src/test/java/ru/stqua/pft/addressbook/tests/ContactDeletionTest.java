package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().contactPage();
    if (app.contact().list().size()==0){
      app.contact().create(new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
              .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
              .withTel("88123456456").withMobile("+78945634").withEmail("testaddress@mail.ru"));
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    List <ContactData> before = app.contact().list();
    int index = before.size()-1;
    app.contact().delete(index);
    app.goTo().returnToContactPage();
    List<ContactData> after = app.contact().list();

    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(index);
    Assert.assertEquals(after, before);
  }




}
