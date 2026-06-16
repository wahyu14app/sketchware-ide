const fs = require('fs'); fs.writeFileSync('debug.keystore', Buffer.from(fs.readFileSync('debug.keystore.base64', 'utf8'), 'base64'));
