-- Retrieve all users
SELECT * FROM forum_test.user `user`
INNER JOIN forum_test.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all individual users
SELECT * FROM forum_test.individual `individual`
INNER JOIN forum_test.user `user` ON `individual`.user_id = `user`.id
INNER JOIN forum_test.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all business users
SELECT * FROM forum_test.business `business`
INNER JOIN forum_test.user `user` ON `business`.user_id = `user`.id
INNER JOIN forum_test.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all admin users
SELECT * FROM forum_test.admin `admin`
INNER JOIN forum_test.user `user` ON `admin`.user_id = `user`.id
INNER JOIN forum_test.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all expired token blacklist rows
SELECT * FROM forum_test.token_blacklist `tokenBlacklist` WHERE `tokenBlacklist`.token_expiration_date_time <= NOW();

-- Retrieve all token blacklist rows
SELECT * FROM forum_test.token_blacklist `tokenBlacklist`;

-- Retrieve all user login records
SELECT * FROM forum_test.user_login_record `userLoginRecord`;