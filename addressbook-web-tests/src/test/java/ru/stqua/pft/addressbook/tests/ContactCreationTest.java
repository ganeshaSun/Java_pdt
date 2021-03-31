package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().list().size()==0){
      app.group().create(new GroupData().withName("test1").withHeader("test2"));
    }
    app.goTo().contactPage();
  }

  @Test
  public void testContactCreation() throws Exception {

    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
            .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
            .withTel("88123456456").withMobile("+78945634").withEmail("testaddress@mail.ru");
    app.contact().create(contact);
    app.goTo().contactPage();
    List <ContactData> after = app.contact().list();

    int max = after.stream().max((o1,o2)->Integer.compare(o1.getId(),o2.getId())).get().getId();
    contact.setId(max);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1,c2)->Integer.compare(c1.getId(),c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }
}
