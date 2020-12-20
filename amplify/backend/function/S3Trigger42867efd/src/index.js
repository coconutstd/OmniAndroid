var AWS = require('aws-sdk');
var s3 = new AWS.S3();
// eslint-disable-next-line
exports.handler = function(event, context) {
  console.log('Received S3 event:', JSON.stringify(event, null, 2));
  // Get the object from the event and show its content type
  const bucket = event.Records[0].s3.bucket.name; //eslint-disable-line
  const key = event.Records[0].s3.object.key; //eslint-disable-line
  console.log(`Bucket: ${bucket}`, `Key: ${key}`);

  var params = {
    Bucket: bucket,
    Key: key + 'public',
    ACL: 'public-read'
  };

   s3.getObject({ Bucket: bucket, Key: key }).promise()
      .then(
          s3.putObject(params).promise()
          .then(() => { callback(null) })
          .catch(err => { callback(err) }))
      .catch(err => {
        console.log('error resizing image: ', err)
        callback(err)
      })

  context.done(null, 'Successfully processed S3 event'); // SUCCESS with message
};
