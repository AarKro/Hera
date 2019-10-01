# Hera Contributing Guidelines

## Branch Management

The develop branch always contains a working version of Hera, up to date with the latest work done in the project.  
The master branch always contains the current live / production version of Hera.  

We create one branch per Issue. These branches can either be merged directly into the develop branch, or first be merged into a designated feature branch.  
Create a feature branch when you work on a topic spanning multiple issues.  

Issue and feature branches are unprotected, so you can always commit directly on to them.  
To merge your work into the develop branch you have to create a pull request. The pull request has to have at least one approving review.  
To merge an issues branch into a feature branch, also create a pull request. This one does not require an approving review, although it is recommended.  

To update the master branch, create a pull request from the develop branch. This pull request needs an approving review of everyone who has contributed code to this pull request.  


## Naming conventions

Branch Type | Pattern
----------- | -------
Issue branch | {group}/{number}-few-descriptive-words
Feature branch | feature/few-descriptive-words

{group} can be one of the following: bugfix, feature, enhancement, maintenance or management.  
{number} corresponds to the issue number the branch is for. Usually the group name can be taken from the Issue labels.  

Preface every commit and pull request with the issue number they are for (#number), this way GitHub can automatically link them to their corresponding issues. Since feature branches don't have a dedicated issue, they don't need to be prefaced with a number.
In addition to this, pull request bodies, both feature and normal issue ones, need to start with a list of their respective issues.

Don’t forget to put a # in front of the issue number!  

## Coding conventions

* Files are indented using tabs.
* For indenting use the regular Java guidelines.
* Avoid nesting ternary operators (inline ifs).
* When an if statement has an else clause, always use blocks for all cases.
