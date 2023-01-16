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
('comment님의 전시관', '설명글', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 3, 'OPEN'),
('gallery님의 전시관', '설명글', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 'OPEN');

INSERT INTO ARTWORK (title, content, image_path, created_at, last_modified_at ,gallery_id, member_id) values
('갤러리1-작품1', '다들 화이팅입니다!', '/1.jpg', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 2),
('갤러리1-작품2', '다들 화이팅입니다!', '/2.jpg', '2022-11-16T23:42:57.764644', '2022-11-16T23:42:57.764644', 1, 6),
('갤러리1-작품3', '다들 화이팅입니다!', '/3.jpg', '2022-11-16T23:43:57.764644', '2022-11-16T23:43:57.764644', 1, 7),
('갤러리1-작품4', '다들 화이팅입니다!', '/4.jpg', '2022-11-16T23:44:57.764644', '2022-11-16T23:44:57.764644', 1, 8),
('갤러리2-작품1', '다들 화이팅입니다!', '/5.jpg', '2022-11-16T23:45:57.764644', '2022-11-16T23:45:57.764644', 2, 8),
('갤러리2-작품2', '다들 화이팅입니다!', '/6.jpg', '2022-11-16T23:46:57.764644', '2022-11-16T23:46:57.764644', 2, 8);

INSERT INTO COMMENT (content ,created_at, last_modified_at, artwork_id, gallery_id, member_id) values
('comment1댓글대스', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 1, 1),
('comment2', '2022-11-12T23:41:58.764644', '2022-11-16T23:41:57.764644', 2, 1, 1),
('comment3', '2022-11-13T23:41:59.764644', '2022-11-16T23:41:57.764644', 3, 1, 2),
('comment444', '2022-11-14T23:41:52.764644', '2022-11-16T23:41:57.764644', null, 1, 4);


INSERT INTO REPLY (content ,created_at, last_modified_at, comment_id, member_id) values
('대댓글', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 1),
('대댓글22', '2022-11-12T23:41:58.764644', '2022-11-16T23:41:57.764644', 1, 1),
('대댓글33', '2022-11-13T23:41:59.764644', '2022-11-16T23:41:57.764644', 1, 2),
('대댓글455', '2022-11-14T23:41:52.764644', '2022-11-16T23:41:57.764644', 1, 4);

INSERT INTO ARTWORK_LIKE (member_id, artwork_id, status, created_at, last_modified_at) values
(1, 4, 'LIKE', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644'),
(2, 4, 'LIKE', '2022-11-11T23:42:57.764644', '2022-11-16T23:42:57.764644'),
(3, 4, 'LIKE', '2022-11-11T23:43:57.764644', '2022-11-16T23:43:57.764644'),
(1, 3, 'LIKE', '2022-11-11T23:44:57.764644', '2022-11-16T23:44:57.764644'),
(2, 3, 'LIKE', '2022-11-11T23:45:57.764644', '2022-11-16T23:45:57.764644');

INSERT INTO CHAT_ROOM (chatted_at, last_chat_message) VALUES
('2022-11-11T23:45:57.764644', '안녕하세요');

INSERT INTO CHAT_ROOM_MEMBER (member_id, chat_room_id) VALUES
(1, 1),
(3, 1);
