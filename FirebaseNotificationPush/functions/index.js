//'use-strict'


const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.firestore.document('alertes/{id}').onCreate((snap, context) => {
    const alerteID = context.params.id;
    console.log("Nom local : " + alerteID);

    var message = {
        alerte: {
            id: alerteID
        }
    };

    return admin.messaging().send(message)
        .then((response) => {
            // Response is a message ID string.
            console.log('Successfully sent message:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });

});

