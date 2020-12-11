# Policy_migration

This project provides the codes and the data for the project of Policy Migration from ABAC to RBAC.

Files Uni_matrix and Video_matrix are two small demos, demonstrating the whole process of migration and evaluation using the two datasets(video and university) which can be accessed on https://www3.cs.stonybrook.edu/~stoller/
software/index.html

Migration_evaluation migrates the RBAC rules from the randomly generated ABAC datasets.

6 datasets are also provided. The name of each file contains 6 parameter. They represent the number of users, the number of resources, the number of user attributes, the number of resource attributes, the number of policies, DIV_factor and the number of possible values for each attribues.


In this report, we used a modified version of the data generator(datagenerator.java) from “Enabling the Deployment of ABAC Policies in RBAC Systems” https://pubmed.ncbi.nlm.nih.gov/30687849/.

The motivation for modifing the data generator is that the original version does not intentionally generate ABAC rules with hierarchy. In our project, we want to test the performance of the migration on generating role with hierarchy. So, we prefer to randomly generate ABAC rules with hierarchy. By saying ABAC rules with hierarchy, we refer to a set of rules which the set of attribute constraints from one ABAC rule(e.g. Rule A) is the superset of the set of attribute constraints from another (e.g. Rule B). And the set of permissions from Rule A is also the super set of that from Rule B.
