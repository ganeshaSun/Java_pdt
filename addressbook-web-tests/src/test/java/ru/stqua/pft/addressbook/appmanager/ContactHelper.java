package ru.stqua.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.Contacts;
import ru.stqua.pft.addressbook.model.GroupData;
import ru.stqua.pft.addressbook.model.Groups;

import java.util.List;


public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }


  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void returnToContactPage() {
    click(By.linkText("home page"));
  }


  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    attach(By.name("photo"), contactData.getPhoto());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
  }

  public void delete(int index) {
    selectContact(index);
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
    contactCash = null;
  }

  public void initContactModification(ContactData contact) {
 /*   selectContactById(contact.getId());
    click(By.xpath("//img[@alt='Edit']"));*/
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", contact.getId()))).click();

  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCash = null;
    returnToContactPage();
  }

  public void modify(ContactData contact) {
    initContactModification(contact);
    fillContactForm(contact, false);
    submitContactModification();
    contactCash = null;
    returnToContactPage();
  }

  private Contacts contactCash = null;

  public Contacts all() {
    if (contactCash != null) {
      return new Contacts(contactCash);
    }
    contactCash = new Contacts();
    List<WebElement> rows = wd.findElements(By.tagName("tr"));
    rows.remove(0);

    if (rows.size() != 0) {
      for (WebElement row : rows) {
        int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        String lastname = cells.get(1).getText();
        String firstname = cells.get(2).getText();
        String address = cells.get(3).getText();
        String allEmails = cells.get(4).getText();
        String allPhones = cells.get(5).getText();
        ContactData contact = new ContactData().withId(id).withLastname(lastname).withFirstName(firstname)
                .withAllPhones(allPhones).withAddress(address).withAllEmails(allEmails);
        contactCash.add(contact);
      }
    }

    return new Contacts(contactCash);
  }

  public int count() {
    return (wd.findElements(By.tagName("tr")).size() - 1);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact);
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getText();
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastname(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  public void addGroup(ContactData contact, Groups groupsLinkedToContact) {
    selectContactById(contact.getId());
    List<WebElement> elements = wd.findElements(By.cssSelector("select[name='to_group']"));
    for (GroupData group : groupsLinkedToContact) {
      for (WebElement element : elements) {
        String name = element.getText();
        int id = Integer.parseInt(element.findElement(By.tagName("option")).getAttribute("value"));
        if (name != group.getName()) {
          wd.findElement(By.cssSelector("option[value='" + id + "']")).click();
        }
      }
      click(By.name("add"));
    }
  }

}
