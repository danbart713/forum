-- Retrieve all users
SELECT * FROM forum.user `user`
INNER JOIN forum.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all individual users
SELECT * FROM forum.individual `individual`
INNER JOIN forum.user `user` ON `individual`.user_id = `user`.id
INNER JOIN forum.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all business users
SELECT * FROM forum.business `business`
INNER JOIN forum.user `user` ON `business`.user_id = `user`.id
INNER JOIN forum.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all admin users
SELECT * FROM forum.admin `admin`
INNER JOIN forum.user `user` ON `admin`.user_id = `user`.id
INNER JOIN forum.email `email` ON `user`.email_id = `email`.id;

-- Retrieve all expired token blacklist rows
SELECT * FROM forum.token_blacklist `tokenBlacklist` WHERE `tokenBlacklist`.token_expiration_date_time <= NOW();

-- Retrieve all token blacklist rows
SELECT * FROM forum.token_blacklist `tokenBlacklist`;

-- Retrieve all user login records
SELECT * FROM forum.user_login_record `userLoginRecord`;