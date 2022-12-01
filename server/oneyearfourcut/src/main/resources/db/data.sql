INSERT INTO MEMBER (nickname, email, created_at, last_modified_at, status, role) VALUES
('galleryPerson', 'test1@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artworkPerson1', 'artworkPerson1@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('commentPerson', 'test3@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('replyPerson', 'test4@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('votePerson', 'test5@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artworkPerson2', 'artworkPerson2@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artworkPerson3', 'artworkPerson3@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER'),
('artworkPerson4', 'artworkPerson4@gmail.com', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 'ACTIVE', 'USER');

INSERT INTO GALLERY (title, content ,created_at, last_modified_at, member_id, status) values
('commentPerson님의 전시관', '설명글', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 3, 'OPEN'),
('galleryPerson님의 전시관', '설명글', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 'OPEN');

INSERT INTO ARTWORK (title, content, image_path, status, created_at, last_modified_at ,gallery_id, member_id) values
('갤러리1-작품1', '다들 화이팅입니다!', '/1.jpg', 'REGISTRATION', '2022-11-16T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 2),
('갤러리1-작품2', '다들 화이팅입니다!', '/2.jpg', 'REGISTRATION', '2022-11-16T23:42:57.764644', '2022-11-16T23:42:57.764644', 1, 6),
('갤러리1-작품3', '다들 화이팅입니다!', '/3.jpg', 'REGISTRATION', '2022-11-16T23:43:57.764644', '2022-11-16T23:43:57.764644', 1, 7),
('갤러리1-작품4', '다들 화이팅입니다!', '/4.jpg', 'REGISTRATION', '2022-11-16T23:44:57.764644', '2022-11-16T23:44:57.764644', 1, 8),
('갤러리2-작품1', '다들 화이팅입니다!', '/4.jpg', 'REGISTRATION', '2022-11-16T23:45:57.764644', '2022-11-16T23:45:57.764644', 2, 8),
('갤러리2-작품2', '다들 화이팅입니다!', '/4.jpg', 'REGISTRATION', '2022-11-16T23:46:57.764644', '2022-11-16T23:46:57.764644', 2, 8);

INSERT INTO COMMENT (content ,created_at, last_modified_at, artwork_id, gallery_id, member_id, COMMENT_STATUS) values
('comment1댓글대스', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 1, 1, 'VALID'),
('comment2', '2022-11-12T23:41:58.764644', '2022-11-16T23:41:57.764644', 2, 1, 1, 'VALID'),
('comment3', '2022-11-13T23:41:59.764644', '2022-11-16T23:41:57.764644', 3, 1, 2, 'DELETED'),
('comment444', '2022-11-14T23:41:52.764644', '2022-11-16T23:41:57.764644', null, 1, 4, 'VALID');


INSERT INTO REPLY (content ,created_at, last_modified_at, comment_id, member_id, Reply_STATUS) values
('대댓글', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644', 1, 1, 'VALID'),
('대댓글22', '2022-11-12T23:41:58.764644', '2022-11-16T23:41:57.764644', 1, 1, 'VALID'),
('대댓글33', '2022-11-13T23:41:59.764644', '2022-11-16T23:41:57.764644', 1, 2, 'DELETED'),
('대댓글455', '2022-11-14T23:41:52.764644', '2022-11-16T23:41:57.764644', 1, 4, 'VALID');

INSERT INTO ARTWORK_LIKE (member_id, artwork_id, status, created_at, last_modified_at) values
(1, 4, 'LIKE', '2022-11-11T23:41:57.764644', '2022-11-16T23:41:57.764644'),
(2, 4, 'LIKE', '2022-11-11T23:42:57.764644', '2022-11-16T23:42:57.764644'),
(3, 4, 'LIKE', '2022-11-11T23:43:57.764644', '2022-11-16T23:43:57.764644'),
(1, 3, 'LIKE', '2022-11-11T23:44:57.764644', '2022-11-16T23:44:57.764644'),
(2, 3, 'LIKE', '2022-11-11T23:45:57.764644', '2022-11-16T23:45:57.764644');


