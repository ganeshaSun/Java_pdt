package ru.stqua.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.Contacts;
import ru.stqua.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().contactPage();
    if (app.contact().all().size()==0) {
      app.goTo().groupPage();
      if (app.group().all().size()==0){
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
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("ModifiedContact").withLastname("LLastName");

    app.contact().modify(contact);
    Contacts after = app.contact().all();
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contact)));
  }




}
