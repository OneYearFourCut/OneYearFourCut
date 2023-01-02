INSERT INTO MEMBER (nickname, email, profile, created_at, last_modified_at, status, role) VALUES
('gallery', 'test1@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artwork1', 'artworkPerson1@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('comment', 'test3@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('reply', 'test4@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('like', 'test5@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artwork2', 'artworkPerson2@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artwork3', 'artworkPerson3@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artwork4', 'artworkPerson4@gmail.com', '/porifile.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER');

INSERT INTO GALLERY (title, content ,created_at, last_modified_at, member_id, status) values
('comment���� ���ð�', '�����', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 3, 'OPEN'),
('gallery���� ���ð�', '�����', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 'OPEN');

INSERT INTO ARTWORK (title, content, image_path, status, created_at, last_modified_at ,gallery_id, member_id) values
('������1-��ǰ1', '�ٵ� ȭ�����Դϴ�!', '/1.jpg', 'REGISTRATION', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 2),
('������1-��ǰ2', '�ٵ� ȭ�����Դϴ�!', '/2.jpg', 'REGISTRATION', '2022-11-16T23:42:57.764644', '2022-11-16T23:42:57.764644', 1, 6),
('������1-��ǰ3', '�ٵ� ȭ�����Դϴ�!', '/3.jpg', 'REGISTRATION', '2022-11-16T23:43:57.764644', '2022-11-16T23:43:57.764644', 1, 7),
('������1-��ǰ4', '�ٵ� ȭ�����Դϴ�!', '/4.jpg', 'REGISTRATION', '2022-11-16T23:44:57.764644', '2022-11-16T23:44:57.764644', 1, 8),
('������2-��ǰ1', '�ٵ� ȭ�����Դϴ�!', '/5.jpg', 'REGISTRATION', '2022-11-16T23:45:57.764644', '2022-11-16T23:45:57.764644', 2, 8),
('������2-��ǰ2', '�ٵ� ȭ�����Դϴ�!', '/6.jpg', 'REGISTRATION', '2022-11-16T23:46:57.764644', '2022-11-16T23:46:57.764644', 2, 8);

INSERT INTO COMMENT (content ,created_at, last_modified_at, artwork_id, gallery_id, member_id, COMMENT_STATUS) values
('comment1��۴뽺', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 1, 1, 'VALID'),
('comment2', '2022-11-12T23:41:58.764644', '2022-11-16T23:41:57.764644', 2, 1, 1, 'VALID'),
('comment3', '2022-11-13T23:41:59.764644', '2022-11-16T23:41:57.764644', 3, 1, 2, 'DELETED'),
('comment444', '2022-11-14T23:41:52.764644', '2022-11-16T23:41:57.764644', null, 1, 4, 'VALID');


INSERT INTO REPLY (content ,created_at, last_modified_at, comment_id, member_id, Reply_STATUS) values
('����', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 1, 'VALID'),
('����22', '2022-11-12T23:41:58.764644', '2022-11-16T23:41:57.764644', 1, 1, 'VALID'),
('����33', '2022-11-13T23:41:59.764644', '2022-11-16T23:41:57.764644', 1, 2, 'DELETED'),
('����455', '2022-11-14T23:41:52.764644', '2022-11-16T23:41:57.764644', 1, 4, 'VALID');

INSERT INTO ARTWORK_LIKE (member_id, artwork_id, status, created_at, last_modified_at) values
(1, 4, 'LIKE', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644'),
(2, 4, 'LIKE', '2022-11-11T23:42:57.764644', '2022-11-16T23:42:57.764644'),
(3, 4, 'LIKE', '2022-11-11T23:43:57.764644', '2022-11-16T23:43:57.764644'),
(1, 3, 'LIKE', '2022-11-11T23:44:57.764644', '2022-11-16T23:44:57.764644'),
(2, 3, 'LIKE', '2022-11-11T23:45:57.764644', '2022-11-16T23:45:57.764644');


