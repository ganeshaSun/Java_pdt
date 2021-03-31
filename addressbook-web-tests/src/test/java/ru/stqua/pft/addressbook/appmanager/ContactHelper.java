package ru.stqua.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqua.pft.addressbook.model.ContactData;

import java.util.ArrayList;
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
    type(By.name("home"), contactData.getHometel());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());

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

  public void delete(int index) {
    selectContact(index);
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }


  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    returnToContactPage();
  }

  public void modify(ContactData contact) {
    initContactModification();
    fillContactForm(contact, false);
    submitContactModification();
    returnToContactPage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }


  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> rows = wd.findElements(By.tagName("tr"));
    rows.remove(0);

    if (rows.size() != 0) {
      for (WebElement row : rows) {
        int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        String lastname = cells.get(1).getText();
        String name = cells.get(2).getText();
        ContactData contact = new ContactData().withId(id).withLastname(lastname).withFirstName(name);
        contacts.add(contact);
      }
    }

    return contacts;
  }
}
