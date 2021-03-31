package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().contactPage();
    if (app.contact().list().size()==0) {
      app.goTo().groupPage();
      if (app.group().list().size()==0){
        app.group().create(new GroupData().withName("test 1").withHeader("head").withFooter("footer"));
      }
      app.goTo().contactPage();
      app.contact().create(new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
              .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
              .withTel("88123456456").withMobile("+78945634").withEmail("testaddress@mail.ru"));
    }
  }

  @Test
  public void testContactModification() throws Exception {
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData().withId(before.get(0).getId()).withFirstName("ModifiedContact").withLastname("LLastName");

    app.contact().modify(contact);
    List<ContactData> after = app.contact().list();
    before.remove(0);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }




}
