package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.Contacts;
import ru.stqua.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1").withHeader("test2"));
    }
    app.goTo().contactPage();
  }

  @Test
  public void testContactCreation() throws Exception {

    Contacts before = app.contact().all();
    File photo = new File("src\\test\\resources\\29.png");
    ContactData contact = new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
            .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
            .withHomePhone("88123456456").withMobilePhone("+78945634").withEmail("testaddress@mail.ru").withWorkPhone("123123")
            .withPhoto(photo);
    app.contact().create(contact);
    app.goTo().contactPage();
    assertThat(app.contact().count(),equalTo(before.size()+1));
    Contacts after = app.contact().all();

    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream()
            .max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId()))));
  }

/*  @Test
  public void testCurrentDir(){
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/28.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }*/
}
