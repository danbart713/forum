DROP DATABASE IF EXISTS forum;

CREATE DATABASE forum;

USE forum;

CREATE TABLE email (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
email_address VARCHAR(256) UNIQUE NOT NULL,
is_registered BOOLEAN DEFAULT FALSE NOT NULL,
is_two_factor_authentication_enabled BOOLEAN DEFAULT FALSE NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id));

CREATE TABLE user (
id INT AUTO_INCREMENT,
user_uuid_id UUID NOT NULL,
display_name VARCHAR(36) UNIQUE NOT NULL,
email_id INT NOT NULL,
password VARCHAR(256) NOT NULL,
user_role VARCHAR(24) NOT NULL,
registration_ip_address VARCHAR(48) NOT NULL,
last_login_date_time TIMESTAMP,
is_user_suspended BOOLEAN DEFAULT FALSE NOT NULL,
is_user_banned BOOLEAN DEFAULT FALSE NOT NULL,
is_password_expired BOOLEAN DEFAULT FALSE NOT NULL,
is_user_locked BOOLEAN DEFAULT FALSE NOT NULL,
is_user_deleted BOOLEAN DEFAULT FALSE NOT NULL,
optimistic_locking_version INT NOT NULL,
user_created_date_time TIMESTAMP NOT NULL,
user_updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_user_email
	FOREIGN KEY (email_id) REFERENCES email (id));

CREATE TABLE individual (
user_id INT NOT NULL,
individual_uuid_id UUID NOT NULL,
individual_created_date_time TIMESTAMP NOT NULL,
individual_updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id),
CONSTRAINT fk_individual_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE admin (
user_id INT NOT NULL,
admin_uuid_id UUID NOT NULL,
first_name VARCHAR(48) NOT NULL,
last_name VARCHAR(48) NOT NULL,
is_super_admin BOOLEAN DEFAULT FALSE NOT NULL,
admin_created_date_time TIMESTAMP NOT NULL,
admin_updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id),
CONSTRAINT fk_admin_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE business (
user_id INT NOT NULL,
business_uuid_id UUID NOT NULL,
business_name VARCHAR(48) NOT NULL,
contact_first_name VARCHAR(48) NOT NULL,
contact_last_name VARCHAR(48) NOT NULL,
business_created_date_time TIMESTAMP NOT NULL,
business_updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id),
CONSTRAINT fk_business_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE subscription (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
is_active BOOLEAN DEFAULT FALSE NOT NULL,
billing_interval VARCHAR(24) NOT NULL,
subscription_level VARCHAR(24) NOT NULL,
subscription_level_type VARCHAR(24) NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_subscription_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE phone (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
phone_number VARCHAR(10) NOT NULL,
phone_extension VARCHAR(6),
phone_type VARCHAR(24) NOT NULL,
is_two_factor_authentication_enabled BOOLEAN DEFAULT FALSE NOT NULL,
is_primary BOOLEAN DEFAULT FALSE NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_phone_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE address (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
address_line_one VARCHAR(256) NOT NULL,
address_line_two VARCHAR(256),
city VARCHAR(45) NOT NULL,
state VARCHAR(14) NOT NULL,
zip VARCHAR(9) NOT NULL,
address_type VARCHAR(24) NOT NULL,
is_primary BOOLEAN DEFAULT FALSE NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_address_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE payment_information (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
credit_card_name VARCHAR(96) NOT NULL,
credit_card_number VARCHAR(19) NOT NULL,
credit_card_expiration VARCHAR(4) NOT NULL,
credit_card_cvc VARCHAR(4) NOT NULL,
credit_card_type VARCHAR(24) NOT NULL,
billing_address_line_one VARCHAR(256) NOT NULL,
billing_address_line_two VARCHAR(256),
billing_city VARCHAR(45) NOT NULL,
billing_state VARCHAR(14) NOT NULL,
billing_zip VARCHAR(9) NOT NULL,
billing_country VARCHAR(56) NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_payment_information_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE profile (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
photo_url VARCHAR(256),
description VARCHAR(5000),
post_count INT DEFAULT 0 NOT NULL,
new_thread_count INT DEFAULT 0 NOT NULL,
post_point_count INT DEFAULT 0 NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_profile_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE user_settings (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_user_settings_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE user_settings_ui (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ui_theme VARCHAR(24) NOT NULL,
user_settings_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_user_settings_ui_user_settings
	FOREIGN KEY (user_settings_id) REFERENCES user_settings (id));

CREATE TABLE ip_address_blacklist (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ip_address VARCHAR(48) NOT NULL,
reason VARCHAR(256) NOT NULL,
admin_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_ip_address_blacklist_admin
	FOREIGN KEY (admin_id) REFERENCES admin (user_id));
	
CREATE TABLE banned_word (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
name VARCHAR(256) NOT NULL,
reason VARCHAR(256) NOT NULL,
admin_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_banned_word_admin
	FOREIGN KEY (admin_id) REFERENCES admin (user_id));
	
CREATE TABLE email_address_blacklist (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
email_address VARCHAR(256) UNIQUE NOT NULL,
reason VARCHAR(256) NOT NULL,
admin_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_email_address_blacklist_admin
	FOREIGN KEY (admin_id) REFERENCES admin (user_id));
	
CREATE TABLE gallery_tag (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
name VARCHAR(24) NOT NULL,
description VARCHAR(256),
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id));

CREATE TABLE gallery (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
title VARCHAR(48),
description VARCHAR(256),
unique_view_count INT DEFAULT 0 NOT NULL,
profile_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_gallery_profile
	FOREIGN KEY (profile_id) REFERENCES profile (id));

CREATE TABLE gallery_view (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ip_address VARCHAR(48),
user_id INT,
gallery_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_gallery_view_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_gallery_view_gallery
	FOREIGN KEY (gallery_id) REFERENCES gallery (id));

CREATE TABLE gallery_gallery_tag (
gallery_id	INT NOT NULL,
gallery_tag_id INT NOT NULL,
uuid_id UUID NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (gallery_id, gallery_tag_id),
CONSTRAINT fk_gallery_gallery_tag_gallery
	FOREIGN KEY (gallery_id) REFERENCES gallery (id),
CONSTRAINT fk_gallery_gallery_tag_gallery_tag
	FOREIGN KEY (gallery_tag_id) REFERENCES gallery_tag (id));
	
CREATE TABLE user_user_follow (
user_id	INT NOT NULL,
followed_user_id INT NOT NULL,
uuid_id UUID NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, followed_user_id),
CONSTRAINT fk_user_user_follow_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_user_follow_followed_user
	FOREIGN KEY (followed_user_id) REFERENCES user (id));

CREATE TABLE gallery_media (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
media_url VARCHAR(256) NOT NULL,
title VARCHAR(48),
description VARCHAR(256),
unique_view_count INT DEFAULT 0 NOT NULL,
gallery_id INT NOT NULL,
gallery_media_type VARCHAR(24) NOT NULL,
user_id INT NOT NULL,
gallery_order INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_gallery_media_gallery
	FOREIGN KEY (gallery_id) REFERENCES gallery (id),
CONSTRAINT fk_gallery_media_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE gallery_media_view (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ip_address VARCHAR(48),
user_id INT,
gallery_media_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_gallery_media_view_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_gallery_media_view_gallery_media
	FOREIGN KEY (gallery_media_id) REFERENCES gallery_media (id));
	
CREATE TABLE thread_category (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
name VARCHAR(24) NOT NULL,
description VARCHAR(256),
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id));

CREATE TABLE thread (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
title VARCHAR(48) NOT NULL,
thread_category_id INT NOT NULL,
created_by_user_id INT NOT NULL,
unique_view_count INT DEFAULT 0 NOT NULL,
post_count INT DEFAULT 0 NOT NULL,
unique_user_post_count INT DEFAULT 0 NOT NULL,
latest_post_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_thread_thread_category
	FOREIGN KEY (thread_category_id) REFERENCES thread_category (id),
CONSTRAINT fk_thread_user
	FOREIGN KEY (created_by_user_id) REFERENCES user (id));

CREATE TABLE thread_view (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ip_address VARCHAR(48),
user_id INT,
thread_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_thread_view_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_thread_view_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id));

CREATE TABLE post (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
body VARCHAR(5000) NOT NULL,
positive_point_count INT DEFAULT 0 NOT NULL,
negative_point_count INT DEFAULT 0 NOT NULL,
total_point_count INT DEFAULT 0 NOT NULL,
thread_order INT NOT NULL,
edited_date_time TIMESTAMP,
is_original BOOLEAN DEFAULT FALSE NOT NULL,
user_id INT NOT NULL,
post_id_in_response_to INT,
thread_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_post_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_post_post
	FOREIGN KEY (post_id_in_response_to) REFERENCES post (id),
CONSTRAINT fk_post_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id));

CREATE TABLE post_media (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
media_url VARCHAR(256) NOT NULL,
title VARCHAR(48),
description VARCHAR(256),
unique_view_count INT DEFAULT 0 NOT NULL,
post_id INT NOT NULL,
post_media_type VARCHAR(24) NOT NULL,
post_order INT NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_post_media_post
	FOREIGN KEY (post_id) REFERENCES post (id),
CONSTRAINT fk_post_media_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE post_media_view (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ip_address VARCHAR(48),
user_id INT,
post_media_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_post_media_view_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_post_media_view_post_media
	FOREIGN KEY (post_media_id) REFERENCES post_media (id));
	
CREATE TABLE user_thread_follow (
user_id	INT NOT NULL,
thread_id INT NOT NULL,
uuid_id UUID NOT NULL,
is_every_post_read BOOLEAN DEFAULT FALSE NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, thread_id),
CONSTRAINT fk_user_thread_follow_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_thread_follow_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id));
	
CREATE TABLE user_post_point (
user_id	INT NOT NULL,
post_id INT NOT NULL,
uuid_id UUID NOT NULL,
post_point TINYINT(1) NOT NULL,
thread_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, post_id),
CONSTRAINT fk_user_post_point_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_post_point_post
	FOREIGN KEY (post_id) REFERENCES post (id),
CONSTRAINT fk_user_post_point_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id));
	
CREATE TABLE user_post_status (
user_id	INT NOT NULL,
post_id INT NOT NULL,
uuid_id UUID NOT NULL,
is_read BOOLEAN DEFAULT FALSE NOT NULL,
thread_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, post_id),
CONSTRAINT fk_user_post_status_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_post_status_post
	FOREIGN KEY (post_id) REFERENCES post (id),
CONSTRAINT fk_user_post_status_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id));
	
CREATE TABLE profile_post_pin (
profile_id	INT NOT NULL,
post_id INT NOT NULL,
uuid_id UUID NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (profile_id, post_id),
CONSTRAINT fk_profile_post_pin_profile
	FOREIGN KEY (profile_id) REFERENCES profile (id),
CONSTRAINT fk_profile_post_pin_post
	FOREIGN KEY (post_id) REFERENCES post (id));
	
CREATE TABLE user_post_reaction_type (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
name VARCHAR(24) NOT NULL,
description VARCHAR(256),
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id));

CREATE TABLE user_post_reaction (
user_id	INT NOT NULL,
post_id INT NOT NULL,
uuid_id UUID NOT NULL,
user_post_reaction_type_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, post_id),
CONSTRAINT fk_user_post_reaction_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_post_reaction_post
	FOREIGN KEY (post_id) REFERENCES post (id),
CONSTRAINT fk_user_post_reaction_user_post_reaction_type
	FOREIGN KEY (user_post_reaction_type_id) REFERENCES user_post_reaction_type (id));
	
CREATE TABLE user_report_category (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
name VARCHAR(24) NOT NULL,
description VARCHAR(256),
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id));

CREATE TABLE thread_report (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
reason VARCHAR(256) NOT NULL,
user_report_category_id INT NOT NULL,
thread_id INT NOT NULL,
user_id	INT NOT NULL,
user_report_status VARCHAR(24) NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_thread_report_user_report_category
	FOREIGN KEY (user_report_category_id) REFERENCES user_report_category (id),
CONSTRAINT fk_thread_report_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id),
CONSTRAINT fk_thread_report_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE post_report (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
reason VARCHAR(256) NOT NULL,
user_report_category_id INT NOT NULL,
post_id INT NOT NULL,
user_id	INT NOT NULL,
user_report_status VARCHAR(24) NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_post_report_user_report_category
	FOREIGN KEY (user_report_category_id) REFERENCES user_report_category (id),
CONSTRAINT fk_post_report_post
	FOREIGN KEY (post_id) REFERENCES post (id),
CONSTRAINT fk_post_report_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE profile_report (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
reason VARCHAR(256) NOT NULL,
user_report_category_id INT NOT NULL,
profile_id INT NOT NULL,
user_id	INT NOT NULL,
user_report_status VARCHAR(24) NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_profile_report_user_report_category
	FOREIGN KEY (user_report_category_id) REFERENCES user_report_category (id),
CONSTRAINT fk_profile_report_profile
	FOREIGN KEY (profile_id) REFERENCES profile (id),
CONSTRAINT fk_profile_report_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE user_bookmark_category (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
name VARCHAR(24) NOT NULL,
description VARCHAR(256),
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_user_bookmark_category_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE user_thread_bookmark (
user_id	INT NOT NULL,
thread_id INT NOT NULL,
uuid_id UUID NOT NULL,
user_bookmark_category_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, thread_id),
CONSTRAINT fk_user_thread_bookmark_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_thread_bookmark_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id),
CONSTRAINT fk_user_thread_bookmark_user_bookmark_category
	FOREIGN KEY (user_bookmark_category_id) REFERENCES user_bookmark_category (id));
	
CREATE TABLE user_post_bookmark (
user_id	INT NOT NULL,
post_id INT NOT NULL,
uuid_id UUID NOT NULL,
user_bookmark_category_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, post_id),
CONSTRAINT fk_user_post_bookmark_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_post_bookmark_post
	FOREIGN KEY (post_id) REFERENCES post (id),
CONSTRAINT fk_user_post_bookmark_user_bookmark_category
	FOREIGN KEY (user_bookmark_category_id) REFERENCES user_bookmark_category (id));
	
CREATE TABLE private_messaging_thread (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
title VARCHAR(48) NOT NULL,
created_by_user_id INT NOT NULL,
post_count INT DEFAULT 0 NOT NULL,
unique_user_post_count INT DEFAULT 0 NOT NULL,
latest_private_messaging_post_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_private_messaging_thread_user
	FOREIGN KEY (created_by_user_id) REFERENCES user (id));
	
CREATE TABLE user_private_messaging_thread (
user_id	INT NOT NULL,
private_messaging_thread_id INT NOT NULL,
uuid_id UUID NOT NULL,
is_every_post_read BOOLEAN DEFAULT FALSE NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, private_messaging_thread_id),
CONSTRAINT fk_user_private_messaging_thread_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_private_messaging_thread_private_messaging_thread
	FOREIGN KEY (private_messaging_thread_id) REFERENCES private_messaging_thread (id));
	
CREATE TABLE private_messaging_post (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
body VARCHAR(5000) NOT NULL,
thread_order INT NOT NULL,
edited_date_time TIMESTAMP,
is_original BOOLEAN DEFAULT FALSE NOT NULL,
user_id INT NOT NULL,
private_messaging_post_id_in_response_to INT,
private_messaging_thread_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_private_messaging_post_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_private_messaging_post_private_messaging_post
	FOREIGN KEY (private_messaging_post_id_in_response_to) REFERENCES private_messaging_post (id),
CONSTRAINT fk_private_messaging_post_private_messaging_thread
	FOREIGN KEY (private_messaging_thread_id) REFERENCES private_messaging_thread (id));

CREATE TABLE private_messaging_post_media (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
media_url VARCHAR(256) NOT NULL,
title VARCHAR(48),
description VARCHAR(256),
unique_view_count INT DEFAULT 0 NOT NULL,
private_messaging_post_id INT NOT NULL,
private_messaging_post_media_type VARCHAR(24) NOT NULL,
post_order INT NOT NULL,
user_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_private_messaging_post_media_private_messaging_post
	FOREIGN KEY (private_messaging_post_id) REFERENCES private_messaging_post (id),
CONSTRAINT fk_private_messaging_post_media_user
	FOREIGN KEY (user_id) REFERENCES user (id));

CREATE TABLE private_messaging_post_media_view (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ip_address VARCHAR(48),
user_id INT,
private_messaging_post_media_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_private_messaging_post_media_view_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_private_messaging_post_media_view_media
	FOREIGN KEY (private_messaging_post_media_id) REFERENCES private_messaging_post_media (id));

CREATE TABLE user_private_messaging_post_status (
user_id	INT NOT NULL,
private_messaging_post_id INT NOT NULL,
uuid_id UUID NOT NULL,
is_read BOOLEAN DEFAULT FALSE NOT NULL,
private_messaging_thread_id INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (user_id, private_messaging_post_id),
CONSTRAINT fk_user_private_messaging_post_status_user
	FOREIGN KEY (user_id) REFERENCES user (id),
CONSTRAINT fk_user_private_messaging_post_status_private_messaging_post
	FOREIGN KEY (private_messaging_post_id) REFERENCES private_messaging_post (id),
CONSTRAINT fk_user_private_messaging_post_status_private_messaging_thread
	FOREIGN KEY (private_messaging_thread_id) REFERENCES private_messaging_thread (id));

CREATE TABLE notification (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
private_messaging_thread_id INT,
thread_id INT,
gallery_media_id INT,
post_id INT,
follower_user_id INT,
is_read	BOOLEAN DEFAULT FALSE NOT NULL,
user_id	INT NOT NULL,
notification_type VARCHAR(24) NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_notification_private_messaging_thread
	FOREIGN KEY (private_messaging_thread_id) REFERENCES private_messaging_thread (id),
CONSTRAINT fk_notification_thread
	FOREIGN KEY (thread_id) REFERENCES thread (id),
CONSTRAINT fk_notification_gallery_media
	FOREIGN KEY (gallery_media_id) REFERENCES gallery_media (id),
CONSTRAINT fk_notification_post
	FOREIGN KEY (post_id) REFERENCES post (id),
CONSTRAINT fk_notification_follower_user
	FOREIGN KEY (follower_user_id) REFERENCES user (id),
CONSTRAINT fk_notification_user
	FOREIGN KEY (user_id) REFERENCES user (id));
	
CREATE TABLE token_blacklist (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
token VARCHAR(1000) NOT NULL,
token_expiration_date_time TIMESTAMP NOT NULL,
token_blacklisted_date_time TIMESTAMP NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id));

CREATE TABLE user_login_record (
id INT AUTO_INCREMENT,
uuid_id UUID NOT NULL,
ip_address VARCHAR(48) NOT NULL,
login_date_time TIMESTAMP NOT NULL,
user_id	INT NOT NULL,
optimistic_locking_version INT NOT NULL,
created_date_time TIMESTAMP NOT NULL,
updated_date_time TIMESTAMP NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_user_login_record_user
	FOREIGN KEY (user_id) REFERENCES user (id));