package bank_project.util;

public class BreadcrumbPrompt extends Prompt {

  private Stack breadcrumbs = new Stack();

  public void appendBreadcrumb(String title) {
    this.breadcrumbs.push(title);
  }

  public void removeBreadcrumb() {
    this.breadcrumbs.pop();
  }

  public String inputMenu() {

    StringBuilder titleBuilder = new StringBuilder();

    for (int i = 0; i < this.breadcrumbs.size(); i++) {
      if (titleBuilder.length() > 0) {
        titleBuilder.append("/");
      }
      titleBuilder.append(this.breadcrumbs.get(i));
    }
    titleBuilder.append("> ");

    return this.inputString(titleBuilder.toString());
  }

}
