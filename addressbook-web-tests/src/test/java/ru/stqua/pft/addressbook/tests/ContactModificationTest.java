package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.Contacts;
import ru.stqua.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().contactPage();
    if (app.db().contacts().size()==0) {
      app.goTo().groupPage();
      if (app.db().groups().size()==0){
        app.group().create(new GroupData().withName("test 1").withHeader("head").withFooter("footer"));
      }
      app.goTo().contactPage();
      app.contact().create(new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
              .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
              .withHomePhone("88123456456").withMobilePhone("+78945634").withEmail("testaddress@mail.ru").withWorkPhone("123123"));
    }
  }

  @Test
  public void testContactModification() throws Exception {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstName("ModifiedContact!!!!!!!1").withLastname("LLastName").withNickname(modifiedContact.getNickname()).withMiddlename(modifiedContact.getMiddlename())
            .withHomePhone(modifiedContact.getHomePhone()).withWorkPhone(modifiedContact.getWorkPhone()).withMobilePhone(modifiedContact.getMobilePhone())
            .withEmail(modifiedContact.getEmail()).withEmail2(modifiedContact.getEmail2()).withEmail3(modifiedContact.getEmail3())
            .withAddress(modifiedContact.getAddress()).withCompany(modifiedContact.getCompany())
            .withTitle(modifiedContact.getTitle());
    app.contact().modify(contact);

    assertThat(app.contact().count(),equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }




}
