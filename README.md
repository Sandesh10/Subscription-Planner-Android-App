# Subscription-Planner-Android-App

## Purpose:
Record and track the payment date for the subscriptions of the services such as Netflix, Amazon Prime, etc. It shows you when the next payment is due. As of now, this does not notify the user with any pop-up notifications. However, I plan to add this feature later.

## Use:
Track the payment date of different subscriptions. It lists how much you have to pay and when it is due. 

## Description:
As of now, this add supports adding and deleting of subscription. A user can add a subscription entry by clicking on the floating action button on the bottom right and delete the entry by clicking on the delete button on the entry. A toast message will be displayed when an item is added or deleted. 

NOTE: Once the user presses the Delete icon, the item will be deleted without asking to confirm. 

## Features:
1. Adding a Subscription:
Adding a Subscription allows users to enter the name, description, amount, and service date. This service date may be a start date or the last payment date or the next payment date. Based on the entered date the app suggests the number of days left for the next payment date. Once entering all these details, the user can press the submit button and a new entry will be added to the main screen.

2. Deleting a Subscription:
A user can delete an already existing subscription by clicking on the delete icon on the card view of the subscription. Once deleted a toast message will be displayed and the item will no longer be available to the user. The user can add the same item again by clicking on the add (+) button.

3. Persistent storage:
The app stores the subscription details persistently by using the SharedPreferences feature.

Building Blocks used:
- button which triggers an action when clicked
- display a pop-up message ("toast")
- display an image/icon
- composed of at least 2 linked activities (Add new subscription)
- pass information from one activity to the next
- save information on disk and use it in future executions


Demo Video link: https://www.youtube.com/watch?v=tB9UU31NhzU
