package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.GroupData;
import ru.stqua.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if(app.db().groups().size()==0){
      app.group().create(new GroupData().withName("test name").withHeader("header"));
    }
  }

  @Test
  public void testGroupModification() throws Exception {
    Groups before = app.db().groups();
    GroupData modifyGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifyGroup.getId()).withName("test1")
            .withHeader("test2").withFooter("test3");
    app.group().modify(group);

    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(modifyGroup).withAdded(group)));
    verifyGroupListInUI();
  }




}
