# Details for P7 Words and Geographic Data in Fall 2025


## Starter Code and Using Git

**_You should have installed all software (Java, Git, VS Code) before completing this project._** You can find the [directions for installation here](https://coursework.cs.duke.edu/201fall25/resources-201/-/blob/main/installingSoftware.md) (including workarounds for submitting without Git if needed).

We'll be using Git and the installation of GitLab at [coursework.cs.duke.edu](https://coursework.cs.duke.edu). All code for classwork will be kept here. Git is software used for version control, and GitLab is an online repository to store code in the cloud using Git.

**[This document details the workflow](https://coursework.cs.duke.edu/201fall25/resources-201/-/blob/main/projectWorkflow.md) for downloading the starter code for the project, updating your code on coursework using Git, and ultimately submitting to Gradescope for autograding.** We recommend that you read and follow the directions carefully when working on a project! While coding, we recommend that you periodically (perhaps when completing a method or small section) push your changes as explained.


## Details on Git with a Partner

You may find it helpful to begin by reading the Working Together section of the [Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/tree/master/git) from the Duke Colab. For more, see the [Git tutoraial by Gitlab](https://docs.gitlab.com/ee/tutorials/make_your_first_git_commit.html) including the link to an [extensive video tutorial](https://www.youtube.com/watch?v=4lxvVj7wlZw) if you prefer that.

One person should fork the starter code and then add their partner as a collaborator on the project. Choose Settings>Members>Invite Members. Then use the autocomplete feature to invite your partner to the project as a *maintainer*. Both of you can now clone and push to this project. See the [gitlab documentation here](https://docs.gitlab.com/ee/user/project/members/).

Now you should be ready to clone the code to your local machines.

1. Both students should clone the same repository and import it into VS Code just like previous projects.  
2. After both students have cloned and imported, one person should make a change (you could just write a comment in the code, for example). Commit and push this change. 
3. The other partner will then issue a git pull request. Simply use the command-line (in the same project directory where you cloned the starter code for the project) and type:
```bash
git pull
```
4. If the other partner now opens the project in VS Code again, they should see the modified code with the edit created by the first partner. 
5. You can continue this workflow: Whenever one person finishes work on the project, they commit and push. Whenever anyone starts work on the project, they begin by downloading the current version from the shared online repository using a git pull command.

This process works as long as only one person is editing at a time, and **you always pull before editing** and remember to **commit/push when finished**. If you forget to pull before editing your local code, you might end up working from an old version of the code different than what is in the shared online gitlab repository. If that happens, you may experience an error when you attempt to push your code back to the shared online repository. 

There are many ways to resolve these conflicts, essentially you just need to pick which of the different versions of the code you want to go with. See the [working together Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/blob/master/git/working_together.md) and the [branching and merging Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/blob/master/git/branching_merging.md) from the Duke Colab for more information. You can also refer to our [Git troubleshooting document](https://coursework.cs.duke.edu/201-public-documentation/resources-201/-/blob/main/troubleshooting.md#git-faq). 

If you run into a merge conflict, one thing that might be confusing is that the editor that opens where you can resolve them may, by default, be [VIM](https://www.vim.org), which can be very unintuitive if you have not used it before. You can either look up the basics there, or if you prefer you can set a different text editor as the default that git uses for editing commit messages, merge conflicts, etc. For example, to make Visual Studio Code the default editor (this was an optional step we suggested during installation, so you may have already done this):
1. Open the command palette on visual studio code (`shift` + `command` + `p` on Mac, or `shift` + `ctrl` + `p` on Windows).
2. Write `Shell Command: Install 'Code' command in path` - it should autocomplete to this, press enter. It may ask for your permission.
3. In a terminal, enter the command `git config --global core.editor "code --wait"`. Now VS Code should be the default editor for git.
4. You can confirm the change by trying `git config --global -e` in a terminal. This should open a VS Code window showing your `git config` file (you don't need to edit, this is just to confirm it worked).

The `--wait` command means that whenever `git` opens something in VS Code for you to edit, it will wait until you close that window/tab before proceeding.

# Implementing the DBSCAN algorithm

DBSCAN was explained in class, but the key concepts are defined below. As you read this explanation, think about what helper methods might be useful and that correspond to the steps here. In general, if you find yourself writing the same code of several lines in more than one place, that's a good candidate for a helper method.

## Finding Clusters/Regions with core points

The first step with DBSCAN is to iterate over every point. In this version of the algorithm, a point
is a `Person201` object with an associated (latitude,longitude). The method `getClusters` you're asked to write has a `Person201[] people` parameter, so iterate over that. The steps in the algorithm are, for
each person/point `p`:

1. If the point/person `p` has already been visited, skip it, otherwise proceed to next steps.
2. Find the `Set<Person201>` points/people that are with `epsilon` of `p`. If this set has
a size greater than or equal to `minPoints`, it's a cluster, and you'll continue with the next step. If the set has a size less than `minPoints` then continue iterating over the rest of the point/person objects.
3. Expand the cluster/region as explained below. The expanded cluster is a `Set<Person201>` started in step 2 and then possibly expanded. This `Set<Person201>` possibly expanded object is added to the `List` that will be returned from `getClusters`.

## Expanding a cluster

You'll likely write a helper method to expand the `Set<Person201>` object/cluster that has at least `minPoints` elements as described above in step 2. At least one of the elements in this set is a core point, but there may be more core points. Conceptually the following steps constitue expanding the cluster;

1. Check every point `p` in the cluster to see if its epsilon-neighborhood has at least `minPoints` in it (there is at least one such point). If so, then `p` is a core point, so add it to a queue of core points to be processed. The queue _only contains_ core points. All points in the initial cluster are marked as 
visited: they are either core points (added to queue) or border/fringe points (in the cluster).
2. While the queue has any elements (like breadth-first-search) dequeue an element, say `p`. Find the epsilon neighborhood of `p`. 
Every point `q` in this neighborhood should be added to the cluster being expanded (and marked as visited). Each point `q` in this 
neighborhood should also be checked to see if it's a core point (at least `minPoints` elements in its epsilon-neighborhood). 
If one of the points in epsilon neighborhood of `q` is a core point, add it to the queue _if it hasn't been visited_. 

The summary: to expand a cluster, first find all the core points in the cluster and add them to a queue. While the queue has elements, dequeue one, and find its epsilon neighborhood. The points in this neighborhood become part of the cluster being expanded. Each point in the neighborhood is checked to see
if it's a core point. If it is (_and it's not visited_), it's added to the queue of core points.

## Summary 

Consider that in the explanations above the phrase _epsilon-neighborhood_ is used frequently. Could this be
a helper method? Consider that the phrase _is a core point_ is used more than once. Could this be a helper
method?