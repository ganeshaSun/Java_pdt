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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size()==0){
      app.group().create(new GroupData().withName("test1").withHeader("test2"));
    }
    app.goTo().contactPage();
  }

  @Test
  public void testContactCreation() throws Exception {

    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
            .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
            .withTel("88123456456").withMobile("+78945634").withEmail("testaddress@mail.ru");
    app.contact().create(contact);
    app.goTo().contactPage();
    Contacts after = app.contact().all();


    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream()
            .max((o1,o2)->Integer.compare(o1.getId(),o2.getId())).get().getId()))));
  }
}
