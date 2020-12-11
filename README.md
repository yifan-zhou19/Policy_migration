# Policy_migration

This repository provides the codes and the datasets for the project of Policy Migration from ABAC to RBAC.

In this project, we use the data generator(datagenerator.java) from “Enabling the Deployment of ABAC Policies in RBAC Systems” (https://pubmed.ncbi.nlm.nih.gov/30687849/) and slightly mordify the original version so that the mordified data generator can intentionally generate ABAC rules which can be better used for creating roles with hierarchy.

The motivation for modifing the data generator is that the original version does not intentionally generate ABAC rules with hierarchy. In our project, we want to test the performance of the migration on generating roles with hierarchy, namely the RBAC_1 model. So, we prefer to randomly generate ABAC rules with hierarchy. By saying ABAC rules with hierarchy, we refer to a set of rules which the set of attribute constraints from one ABAC rule(e.g. Rule A) is the superset of the set of attribute constraints from another rule (e.g. Rule B). And the set of permissions from Rule A is also the superset of that from Rule B. 

For example, <[agegroup=teen],[videotype=premium],[view]> and <[agegroup=teen,usertype=member],[videotype=premium],[view,share]> means that teenage users can only view the premium videos and teenage users who are also members can view and share the premium videos.

Files Uni_matrix and Video_matrix are two small demos, demonstrating the whole process of migration and evaluation using the two datasets(video and university) which can be accessed on https://www3.cs.stonybrook.edu/~stoller/
software/index.html

Migration_evaluation migrates the RBAC rules from the randomly generated ABAC datasets.

6 datasets are also provided. The name of each file contains 6 parameter. They represent the number of users, the number of resources, the number of user attributes, the number of resource attributes, the number of policies, DIV_factor and the number of possible values for each attribues.



