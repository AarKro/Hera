# Hera Contributing Guidelines

**Disclaimer**: These guidelines are here to guide you into the general directions of how we want to collaborate on this project. We do realise that sometimes these rules can be a bit strict, which is why there will be exceptions and why we don't strictly enforce them. Still try to be in line with the contributing guidelines most of the time :)

## Branch Management

The develop branch always contains a working version of Hera, up to date with the latest work done in the project.  
The master branch always contains the current live / production version of Hera.  

We create one branch per Issue. These branches can either be merged directly into the develop branch, or first be merged into a designated feature branch.  
Create a feature branch when you work on a topic spanning multiple issues.  

Issue and feature branches are unprotected, so you can always commit directly on to them.  
To merge your work into the develop branch you have to create a pull request. The pull request has to have at least one approving review.  
To merge an issue branch into a feature branch, also create a pull request. This one does not require an approving review, although it is recommended.  

To update the master branch, create a pull request from the develop branch. This pull request needs an approving review of everyone who has contributed code to this pull request.  


## Naming conventions

Branch Type | Pattern
----------- | -------
Issue branch | {group}/{number}-few-descriptive-words
Feature branch | feature/{number}-few-descriptive-words

{group} can be one of the following: bug, hotfix, release, command, change, db, website, api, feature.
{number} corresponds to the issue number the branch is for. Usually the group name can be taken from the issue labels.  

Preface every pull request with the issue number it is for (#number).
In addition to this, pull request bodies, both feature and normal issue ones, need to start with a list of their respective issues.

Features are represented by normal Github issues. Preface them with '[Feature]'. They should contain a short description of what they are about. Every issue that is part of a feature should mention said feature (#number) at the beginning of its description. 

## Coding conventions

* Files are indented using tabs.
* For indenting use the regular Java guidelines.
* Avoid nesting ternary operators (inline ifs).
* When an if statement has an else clause, always use blocks for all cases.
