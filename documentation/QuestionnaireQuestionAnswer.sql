-- =========================
-- 1. 插入三套问卷（questionnaire）
-- =========================
INSERT INTO questionnaire (title, description, status)
VALUES
    ('兴趣测评问卷', '用于评估学生在学习、职业、社交等方面的兴趣偏好，为后续规划提供参考。', 1),
    ('能力测评问卷', '用于评估学生的学习能力、实践能力、沟通能力、时间管理等综合素质。', 1),
    ('自我评价问卷', '了解学生的自我认知、目标、压力来源与成长意愿，为个性化规划提供主观依据。', 1);
-- =========================



-- =========================
-- 2. 插入 question（每题 options 都是 JSON，包含 key、label、tags、weights）
--    说明：weights 的字段为三个方向的加分（kaoyan, kaogong, jiuye）。后端会把用户选项对应的 weights 累加到每个方向上。
-- =========================

-- =========================
-- 2.1 兴趣测评问卷（10题）
-- =========================
-- Q1
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '我更倾向于在学习中……',
           1,
           '[
             {"key":"A","label":"深入研究一个具体问题","tags":["深入研究"],"weights":{"kaoyan":5,"kaogong":0,"jiuye":1}},
             {"key":"B","label":"完成具有创造性的任务","tags":["完成创造性任务"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":4}},
             {"key":"C","label":"与他人合作完成任务","tags":["与他人合作"],"weights":{"kaoyan":0,"kaogong":2,"jiuye":3}},
             {"key":"D","label":"处理逻辑性和结构性强的任务","tags":["处理逻辑性结构性任务"],"weights":{"kaoyan":3,"kaogong":2,"jiuye":3}}
           ]',
           5, 1
       );

-- Q2 (多选)
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '以下哪些活动你觉得最有吸引力？（可多选）',
           2,
           '[
             {"key":"A","label":"查资料、写论文","tags":["喜欢写作"],"weights":{"kaoyan":5,"kaogong":0,"jiuye":0}},
             {"key":"B","label":"组织活动","tags":["善于组织活动"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":2}},
             {"key":"C","label":"动手制作或实验","tags":["喜欢实践"],"weights":{"kaoyan":3,"kaogong":0,"jiuye":4}},
             {"key":"D","label":"与人沟通交流","tags":["擅长沟通"],"weights":{"kaoyan":0,"kaogong":2,"jiuye":4}},
             {"key":"E","label":"编写或调试代码","tags":["热爱技术"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5}}
           ]',
           5, 2
       );

-- Q3
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '空闲时你更愿意……',
           1,
           '[
             {"key":"A","label":"阅读书籍或查资料","tags":["喜欢阅读"],"weights":{"kaoyan":4,"kaogong":0,"jiuye":1}},
             {"key":"B","label":"社交、聊天","tags":["社交、聊天"],"weights":{"kaoyan":0,"kaogong":2,"jiuye":3}},
             {"key":"C","label":"观看科技或技术类内容","tags":["科技技术"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":4}},
             {"key":"D","label":"做一些创意类设计","tags":["创意类设计"],"weights":{"kaoyan":1,"kaogong":0,"jiuye":4}}
           ]',
           5, 3
       );

-- Q4
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '在长期发展上，你更看重……',
           1,
           '[
             {"key":"A","label":"学术深造机会","tags":["学术深造"],"weights":{"kaoyan":5,"kaogong":0,"jiuye":1}},
             {"key":"B","label":"稳定的工作环境","tags":["稳定工作"],"weights":{"kaoyan":0,"kaogong":4,"jiuye":2}},
             {"key":"C","label":"行业前景与成长空间","tags":["行业前景与成长"],"weights":{"kaoyan":2,"kaogong":1,"jiuye":4}},
             {"key":"D","label":"高薪和技术提升","tags":["高薪技术提升"],"weights":{"kaoyan":1,"kaogong":0,"jiuye":5}}
           ]',
           5, 4
       );

-- Q5
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你更愿意把大量时间投入到……',
           1,
           '[
             {"key":"A","label":"科研和论文研究","tags":["科研论文"],"weights":{"kaoyan":5,"kaogong":0,"jiuye":1}},
             {"key":"B","label":"尝试不同的新事物","tags":["尝试新事物"],"weights":{"kaoyan":1,"kaogong":0,"jiuye":4}},
             {"key":"C","label":"技术项目开发","tags":["技术项目"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5}},
             {"key":"D","label":"与人沟通协作的任务","tags":["沟通协作"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":3}}
           ]',
           5, 5
       );

-- Q6
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你对重复性高的任务的接受程度是？',
           1,
           '[
             {"key":"A","label":"可以长期坚持","tags":["重复耐受度高"],"weights":{"kaoyan":1,"kaogong":3,"jiuye":2}},
             {"key":"B","label":"短期可以，长期无法接受","tags":["重复耐受度中"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":1}},
             {"key":"C","label":"完全无法接受","tags":["重复耐受度低"],"weights":{"kaoyan":0,"kaogong":1,"jiuye":0}}
           ]',
           3, 6
       );

-- Q7 (多选)
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你在以下哪些场景中感觉最轻松？（可多选）',
           2,
           '[
             {"key":"A","label":"独立阅读或研究","tags":["独立研究"],"weights":{"kaoyan":4,"kaogong":0,"jiuye":1}},
             {"key":"B","label":"与团队一起完成任务","tags":["团队协作"],"weights":{"kaoyan":0,"kaogong":2,"jiuye":3}},
             {"key":"C","label":"表达、演讲、发言","tags":["表达演讲"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":2}},
             {"key":"D","label":"操作设备或编写代码","tags":["操作设备编写代码"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":4}}
           ]',
           5, 7
       );

-- Q8
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你觉得最符合你性格的描述是？',
           1,
           '[
             {"key":"A","label":"安静沉稳，喜欢思考","tags":["性格偏静"],"weights":{"kaoyan":3,"kaogong":0,"jiuye":1}},
             {"key":"B","label":"善于交流，外向活跃","tags":["性格外向"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":3}},
             {"key":"C","label":"条理清晰，喜欢逻辑","tags":["性格条理"],"weights":{"kaoyan":3,"kaogong":2,"jiuye":2}},
             {"key":"D","label":"创造力强，想法多","tags":["性格创造"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":4}}
           ]',
           4, 8
       );

-- Q9
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '如果让你在以下任务中选择一个，你会选？',
           1,
           '[
             {"key":"A","label":"做研究或调查","tags":["研究调查"],"weights":{"kaoyan":5,"kaogong":0,"jiuye":1}},
             {"key":"B","label":"主持活动或交流","tags":["主持活动"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":2}},
             {"key":"C","label":"开发或调试系统","tags":["开发调试"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5}},
             {"key":"D","label":"做设计或创新任务","tags":["设计创新任务"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":4}}
           ]',
           5, 9
       );

-- Q10 文本题
INSERT INTO `question` (questionnaire_id, content, type, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '请描述你最喜欢的学习方式（可简答）',
           3,
           0, 10
       );
-- =========================



-- =========================
-- 2.2 能力测评问卷（10题）
-- =========================
-- A1
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你在自我学习中遇到难题时通常会……',
           1,
           '[
             {"key":"A","label":"独立查资料解决","tags":["独立解决难题"],"weights":{"kaoyan":3,"kaogong":0,"jiuye":2}},
             {"key":"B","label":"向老师或同学请教","tags":["请教他人"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":2}},
             {"key":"C","label":"搁置一段时间后再看","tags":["稍后处理难题"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}},
             {"key":"D","label":"容易放弃","tags":["容易放弃"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           5, 1
       );

-- A2
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你的时间管理能力评价如何？',
           1,
           '[
             {"key":"A","label":"非常强，能严格按照计划执行","tags":["严格按照计划"],"weights":{"kaoyan":3,"kaogong":2,"jiuye":2}},
             {"key":"B","label":"中等，需要提醒","tags":["需要提醒"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":1}},
             {"key":"C","label":"较弱，经常无法按时完成","tags":["经常无法按时完成"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           4, 2
       );

-- A3 (多选)
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '以下哪些能力你认为自己相对较强？（可多选）',
           2,
           '[
             {"key":"A","label":"逻辑分析能力","tags":["逻辑分析能力强"],"weights":{"kaoyan":3,"kaogong":2,"jiuye":2}},
             {"key":"B","label":"沟通表达能力","tags":["沟通表达能力强"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":4}},
             {"key":"C","label":"动手实践能力","tags":["动手实践能力强"],"weights":{"kaoyan":1,"kaogong":0,"jiuye":4}},
             {"key":"D","label":"创新能力","tags":["创新能力强"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":3}},
             {"key":"E","label":"组织协调能力","tags":["组织协调能力强"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":2}}
           ]',
           5, 3
       );

-- A4
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '面对压力时，你通常的状态是？',
           1,
           '[
             {"key":"A","label":"能保持稳定并完成任务","tags":["抗压能力强"],"weights":{"kaoyan":2,"kaogong":2,"jiuye":2}},
             {"key":"B","label":"略受影响，但能坚持","tags":["抗压能力中等"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":1}},
             {"key":"C","label":"容易焦虑影响表现","tags":["抗压能力弱"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           4, 4
       );

-- A5
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你认为自己的技术技能水平如何？',
           1,
           '[
             {"key":"A","label":"非常熟练","tags":["技术能力熟练"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5}},
             {"key":"B","label":"一般，需要指导","tags":["技术技能一般"],"weights":{"kaoyan":1,"kaogong":0,"jiuye":3}},
             {"key":"C","label":"较弱，需要大量练习","tags":["技术技能较弱"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           4, 5
       );

-- A6
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你能否独立完成一个小型项目？',
           1,
           '[
             {"key":"A","label":"可以，从设计到实现完全没问题","tags":["可独立完成小项目"],"weights":{"kaoyan":2,"kaogong":1,"jiuye":4}},
             {"key":"B","label":"在指导下可以完成","tags":["在指导下可以完成小项目"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":2}},
             {"key":"C","label":"很困难","tags":["很难独立完成小项目"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           4, 6
       );

-- A7
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你对陌生领域的学习态度更接近？',
           1,
           '[
             {"key":"A","label":"主动探索","tags":["主动探索陌生领域"],"weights":{"kaoyan":3,"kaogong":1,"jiuye":2}},
             {"key":"B","label":"被动接受","tags":["被动接受陌生领域"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}},
             {"key":"C","label":"抗拒学习","tags":["抗拒学习陌生领域"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           3, 7
       );

-- A8
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '团队合作中，你更适合？',
           1,
           '[
             {"key":"A","label":"负责人/组织者","tags":["成为团队组织者"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":2}},
             {"key":"B","label":"执行者/协作者","tags":["成为团队领导者"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":2}},
             {"key":"C","label":"独立完成自己的部分","tags":["独立完成自己部分"],"weights":{"kaoyan":2,"kaogong":1,"jiuye":3}}
           ]',
           4, 8
       );

-- A9
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你认为自己在沟通和表达方面的能力？',
           1,
           '[
             {"key":"A","label":"非常强","tags":["沟通表达非常强"],"weights":{"kaoyan":0,"kaogong":4,"jiuye":4}},
             {"key":"B","label":"一般","tags":["沟通表达一般"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":2}},
             {"key":"C","label":"较弱","tags":["沟通表达较弱"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           3, 9
       );

-- A10 文本
INSERT INTO `question` (questionnaire_id, content, type, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '请写下你认为自己最突出的能力（可简答）',
           3,
           0, 10
       );
-- =========================



-- =========================
-- 2.3 自我评价问卷（10题）
-- =========================
-- S1
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你当前对自己整体的学习状态满意度如何？',
           1,
           '[
             {"key":"A","label":"非常满意","tags":["自我满意度高"],"weights":{"kaoyan":2,"kaogong":1,"jiuye":1}},
             {"key":"B","label":"一般","tags":["自我满意度一般"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":1}},
             {"key":"C","label":"不满意","tags":["自我满意度低"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           3, 1
       );

-- S2 文本
INSERT INTO `question` (questionnaire_id, content, type, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你认为自己目前最大的优势是什么？（可简答）',
           3,
           0, 2
       );

-- S3 文本
INSERT INTO `question` (questionnaire_id, content, type, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你认为自己目前最需要提升的方面是什么？（可简答）',
           3,
           0, 3
       );

-- S4
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你对未来一年内的发展最重视哪方面？',
           1,
           '[
             {"key":"A","label":"提升专业能力","tags":["关注专业能力提升"],"weights":{"kaoyan":3,"kaogong":1,"jiuye":3}},
             {"key":"B","label":"准备升学","tags":["关注升学"],"weights":{"kaoyan":4,"kaogong":0,"jiuye":1}},
             {"key":"C","label":"找实习或就业","tags":["关注就业"],"weights":{"kaoyan":0,"kaogong":1,"jiuye":4}},
             {"key":"D","label":"提升综合素质","tags":["关注综合素质"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":2}}
           ]',
           4, 4
       );

-- S5
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你更倾向的未来发展方向是？',
           1,
           '[
             {"key":"A","label":"考研","tags":["倾向考研"],"weights":{"kaoyan":10,"kaogong":0,"jiuye":0}},
             {"key":"B","label":"考公","tags":["倾向考公"],"weights":{"kaoyan":0,"kaogong":10,"jiuye":0}},
             {"key":"C","label":"就业","tags":["倾向就业"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":10}},
             {"key":"D","label":"未确定","tags":["倾向未确定"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           5, 5
       );

-- S6
INSERT INTO `question` (questionnaire_id, content, type, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你下一阶段的主要目标是什么？（可简答）',
           3,
           0, 6
       );

-- S7 (多选)
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你目前的压力来源主要是？（可多选）',
           2,
           '[
             {"key":"A","label":"课程压力","tags":["课业压力"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":1}},
             {"key":"B","label":"就业压力","tags":["就业压力"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":2}},
             {"key":"C","label":"升学压力","tags":["升学压力"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":0}},
             {"key":"D","label":"家庭压力","tags":["家庭压力"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}},
             {"key":"E","label":"自我要求","tags":["自我要求压力"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":1}}
           ]',
           4, 7
       );

-- S8
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你对自己的自律程度评价是？',
           1,
           '[
             {"key":"A","label":"非常自律","tags":["非常自律"],"weights":{"kaoyan":3,"kaogong":1,"jiuye":2}},
             {"key":"B","label":"一般","tags":["自律程度一般"],"weights":{"kaoyan":1,"kaogong":0,"jiuye":1}},
             {"key":"C","label":"不太自律","tags":["不太自律"],"weights":{"kaoyan":0,"kaogong":0,"jiuye":0}}
           ]',
           3, 8
       );

-- S9
INSERT INTO `question` (questionnaire_id, content, type, options, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你更喜欢怎样的学习或成长节奏？',
           1,
           '[
             {"key":"A","label":"稳健型","tags":["稳健型节奏"],"weights":{"kaoyan":2,"kaogong":1,"jiuye":1}},
             {"key":"B","label":"适中型","tags":["适中型节奏"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":2}},
             {"key":"C","label":"高强度","tags":["高强度节奏"],"weights":{"kaoyan":3,"kaogong":0,"jiuye":2}}
           ]',
           3, 9
       );

-- S10 文本
INSERT INTO `question` (questionnaire_id, content, type, score, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '请描述你理想的未来3年目标（可简答）',
           3,
           0, 10
       );
-- =========================



-- =========================
-- 测试用户完整答题数据
-- =========================
-- 兴趣测评问卷（questionnaire_id=1）答案（82，45，30）
INSERT INTO answer (user_id, question_id, answer_content, score)
VALUES
    (1, 1, 'A', 5),
    (1, 2, 'A,E', 5),
    (1, 3, 'A', 5),
    (1, 4, 'A', 5),
    (1, 5, 'A', 5),
    (1, 6, 'A', 3),
    (1, 7, 'A,D', 5),
    (1, 8, 'A', 4),
    (1, 9, 'A', 5),
    (1, 10, '喜欢独立查阅文献、深度钻研专业知识，通过思维导图梳理考点', 0),

    (1, 11, 'A', 5),
    (1, 12, 'A', 4),
    (1, 13, 'A,D', 5),
    (1, 14, 'A', 4),
    (1, 15, 'A', 4),
    (1, 16, 'A', 4),
    (1, 17, 'A', 3),
    (1, 18, 'C', 4),
    (1, 19, 'B', 3),
    (1, 20, '自主学习能力强，擅长总结考点和梳理知识体系，逻辑分析能力突出', 0),

    (1, 21, 'A', 3),
    (1, 22, '目标明确，自律性强，能长期坚持学习，对专业知识掌握扎实', 0),
    (1, 23, '需要提升专业前沿知识储备，加强科研论文写作能力', 0),
    (1, 24, 'B', 4),
    (1, 25, 'A', 5),
    (1, 26, '考上目标院校的专业硕士，扎实掌握专业核心知识，为科研工作打基础', 0),
    (1, 27, 'C,E', 4),
    (1, 28, 'A', 3),
    (1, 29, 'C', 3),
    (1, 30, '第一年成功上岸研究生，第二年深入专业研究方向发表论文，第三年获得优秀毕业生称号并争取读博机会', 0);



-- =========================
-- answer + question.options 计算方向分数
-- =========================
SELECT
    SUM(JSON_EXTRACT(q.options, CONCAT('$.', a.answer_content, '.weights.kaoyan'))) AS kaoyan_score,
    SUM(JSON_EXTRACT(q.options, CONCAT('$.', a.answer_content, '.weights.kaogong'))) AS kaogong_score,
    SUM(JSON_EXTRACT(q.options, CONCAT('$.', a.answer_content, '.weights.jiuye'))) AS jiuye_score
FROM answer a
         JOIN question q ON a.question_id = q.id
WHERE a.user_id = 1;


select answer_content from answer;





-- =========================
-- 3. 插入 plan_template
-- =========================
INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '考研',
           '考研模板1-基础型',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：公共课基础复习，系统学习数学/英语基础知识框架',
                   '第3-4个月：专业课入门，建立知识结构体系，开始做章节练习',
                   '第5-6个月：公共课强化训练，开始真题第一轮',
                   '第7-8个月：专业课强化与真题精做',
                   '第9-10个月：公共课模拟训练+查缺补漏',
                   '第11-12个月：全真模拟与冲刺复盘'
                               ),
                   'study_path', JSON_ARRAY(
                           'Math：基础→强化→真题→模拟',
                           'English：长难句→阅读强化→作文模板积累→模拟训练',
                           '专业课：教材精读→重难点笔记→真题→专题突破'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '建立完整复习框架',
                           '公共课与专业课并行推进',
                           '完成至少 8 套真题与 4 套模拟题',
                           '定期复盘错题、整理知识笔记'
                                ),
                   'exam_reference', '全国硕士研究生考试一般在每年12月下旬举行；初试成绩次年2月公布；复试在3-4月进行。',
                   'risks', JSON_ARRAY(
                           '基础不扎实导致后期提升缓慢',
                           '计划执行力不足，复习进度落后',
                           '真题复盘不够深入导致提分瓶颈'
                            ),
                   'suggestion', JSON_ARRAY(
                           '尽早形成自己的复习节奏',
                           '保持每天至少 4-6 小时稳定学习时间',
                           '建立错题本与知识体系图谱'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '考研',
           '考研模板2-科研型',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：夯实数学基础并系统学习专业课理论框架',
                   '第3-5个月：开始专业课深度学习，查阅核心教材与论文',
                   '第6-7个月：公共课强化训练，真题第一轮',
                   '第8-10个月：专业课深入理解重点章节，完成真题二刷',
                   '第11-12个月：冲刺阶段做全真模拟并进行查漏补缺'
                               ),
                   'study_path', JSON_ARRAY(
                           '高级专业课学习：核心教材+教授推荐书单',
                           '科研阅读路径：检索专业方向论文→做文献综述笔记',
                           '公共课策略：注重逻辑推演与阅读速度提升'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '形成专业知识深度理解能力',
                           '累计 20+ 篇论文阅读记录',
                           '建立专业课错题-难点数据库'
                                ),
                   'exam_reference', '建议关注目标院校历年复试侧重方向（学术潜力、科研基础）；提前准备英文口语与科研经历描述。',
                   'risks', JSON_ARRAY(
                           '过度投入专业课导致公共课掉分',
                           '研究深度不够会影响复试表现',
                           '压力过大导致学习节奏失衡'
                            ),
                   'suggestion', JSON_ARRAY(
                           '维持数学与英语的最低学习量',
                           '每两周进行一次科研方向 mini review',
                           '适当参加学术讲座提升科研敏感度'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '考研',
           '考研模板3-扶持型',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：公共课基础入门（数学与英语）',
                   '第3-4个月：专业课基础精读',
                   '第5-6个月：公共课第一轮强化',
                   '第7-8个月：专业课真题入手+错题整理',
                   '第9-10个月：公共课重点突破',
                   '第11-12个月：模拟+查缺补漏'
                               ),
                   'study_path', JSON_ARRAY(
                           '公共课优先：数学基础、英语阅读与词汇',
                           '专业课采取“教材→重难点→真题”三步走策略',
                           '跟随机构主线规划提升节奏'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '建立稳定的每日学习习惯（3-4小时起）',
                           '完成公共课基础积累',
                           '真题至少完成 5 套'
                                ),
                   'exam_reference', '建议选择专业课难度中等的目标院校；关注其录取线与复试规则。',
                   'risks', JSON_ARRAY(
                           '自律不足导致计划无法落地',
                           '公共课基础薄弱导致焦虑',
                           '节奏过慢无法完成复习目标'
                            ),
                   'suggestion', JSON_ARRAY(
                           '采用番茄时间法提高专注力',
                           '每周制定可量化的小目标',
                           '尽量在固定时间学习形成节奏'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '考公',
           '考公模板1-综合岗',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：行测基础学习（数量关系、判断推理）',
                   '第3-4个月：申论入门与基础写作训练',
                   '第5-6个月：行测刷题强化',
                   '第7-8个月：申论全面提升，专项练习',
                   '第9-10个月：真题精做与模拟',
                   '第11-12个月：冲刺阶段、系统复盘'
                               ),
                   'study_path', JSON_ARRAY(
                           '行测：基础→专项→套题→真题',
                           '申论：结构→素材→审题→限时写作'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '掌握行测五大模块核心方法',
                           '每周完成 3-4 套限时训练',
                           '申论输出 15+ 份练习作文'
                                ),
                   'exam_reference', '国家公务员考试一般在11月报名，次年1月笔试；省考时间因省份不同在3-6月。',
                   'risks', JSON_ARRAY(
                           '行测提分慢导致焦虑',
                           '申论无框架导致得分低',
                           '练习量不足'
                            ),
                   'suggestion', JSON_ARRAY(
                           '尽早建立申论写作框架',
                           '保持每日 2 小时行测训练',
                           '真题优先、机构题次之'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '考公',
           '考公模板2-高分冲刺型',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：行测高难题型突破，专项训练',
                   '第3-5个月：申论深度训练（套题+文章分析）',
                   '第6-8个月：行测与申论并行冲刺',
                   '第9-12个月：全真模拟与复盘'
                               ),
                   'study_path', JSON_ARRAY(
                           '行测：定量专项突破→逻辑强化→资料分析技巧',
                           '申论：高分范文赏析→深度分析→文章结构创新'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '行测每周完成 6 套题',
                           '申论每周输出 3 篇作文',
                           '形成个人申论模板框架'
                                ),
                   'exam_reference', '重点关注岗位竞争比与招录条件，提前准备资格审查材料。',
                   'risks', JSON_ARRAY(
                           '高强度训练容易疲惫',
                           '忽略岗位性质导致方向偏差'
                            ),
                   'suggestion', JSON_ARRAY(
                           '保持节奏，避免过度刷题',
                           '以真题为主、模拟题为辅'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '考公',
           '考公模板3-基础薄弱扶持型',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：行测基础知识建立',
                   '第3-4个月：申论结构化写作练习',
                   '第5-7个月：行测专项突破',
                   '第8-10个月：申论套题训练',
                   '第11-12个月：模拟考试'
                               ),
                   'study_path', JSON_ARRAY(
                           '行测优先学习常识与判断推理',
                           '申论从积累素材开始逐步写作'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '每日保持至少 1.5 小时行测刷题',
                           '申论每周写1篇作文'
                                ),
                   'exam_reference', '建议优先报省考竞争压力较低岗位。',
                   'risks', JSON_ARRAY(
                           '基础薄弱导致进度缓慢',
                           '学习节奏不稳定'
                            ),
                   'suggestion', JSON_ARRAY(
                           '建立每周复盘机制',
                           '必要时选择辅导班提升效率'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '就业',
           '就业模板1-标准求职路线',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：职业方向探索、技能盘点',
                   '第3-4个月：简历优化、基础项目准备',
                   '第5-6个月：实习申请与笔试准备',
                   '第7-9个月：面试准备、强化项目表达',
                   '第10-12个月：校招求职、补充实习'
                               ),
                   'study_path', JSON_ARRAY(
                           '掌握行业所需基本技能',
                           '输出至少一个完整项目',
                           '准备行为面试与技术面试'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '制作专业简历',
                           '建立个人作品集或项目文档',
                           '投递实习岗位'
                                ),
                   'exam_reference', '关注各大公司校招流程：春招（2-4月）、秋招（8-10月）。',
                   'risks', JSON_ARRAY(
                           '技能准备不足',
                           '简历表达不佳',
                           '缺少项目经验'
                            ),
                   'suggestion', JSON_ARRAY(
                           '参加行业竞赛或做开源项目',
                           '建立实习经验提升竞争力'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '就业',
           '就业模板2-技术岗',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：补齐基础能力（编程、数据结构、数学）',
                   '第3-4个月：系统学习后端/前端/算法能力',
                   '第5-7个月：做项目并准备面试题库',
                   '第8-10个月：投递实习、技术面试准备',
                   '第11-12个月：补充算法与项目深度'
                               ),
                   'study_path', JSON_ARRAY(
                           '技术栈学习：Java/Python/前端等',
                           '工程化能力：Git、CI/CD',
                           '算法学习：LeetCode 高频题'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '完成至少 2 个技术项目',
                           '掌握主流框架',
                           '通过 200+ 道算法题'
                                ),
                   'exam_reference', '关注互联网企业校招批次：提前批、秋招、秋招补录。',
                   'risks', JSON_ARRAY(
                           '只学框架不理解底层',
                           '项目不够真实导致面试不通过'
                            ),
                   'suggestion', JSON_ARRAY(
                           '做真实项目而不是模板项目',
                           '准备系统设计和行为面试'
                                 )
           ));

INSERT INTO plan_template(direction, template_name, evaluation_rules)
VALUES (
           '就业',
           '就业模板3-能力薄弱型',
           JSON_OBJECT(
                   'timeline', JSON_ARRAY(
                   '第1-2个月：基础能力建设（沟通、办公软件、基础知识）',
                   '第3-4个月：职业方向定位、简历制作',
                   '第5-7个月：提升专业技能或证书学习',
                   '第8-10个月：实习申请、兼职实践',
                   '第11-12个月：求职准备'
                               ),
                   'study_path', JSON_ARRAY(
                           '优先补充最基本的求职技能',
                           '选择入门难度较低的岗位方向',
                           '通过证书或短期培训提升竞争力'
                                 ),
                   'key_tasks', JSON_ARRAY(
                           '准备专业简历',
                           '完成基础技能提升',
                           '获取实践经验'
                                ),
                   'exam_reference', '建议多关注中小企、校企合作以及实习机会。',
                   'risks', JSON_ARRAY(
                           '能力薄弱导致岗位选择受限',
                           '缺乏实习经历导致竞争力弱'
                            ),
                   'suggestion', JSON_ARRAY(
                           '参加职业培训班提升效率',
                           '从简单岗位入手逐步提升'
                                 )
           ));

-- =========================
-- 更新三套就业模版
-- =========================
update plan_template
set template_name = '就业模板1-通用型',
    evaluation_rules = JSON_OBJECT(
            'timeline', JSON_ARRAY(
                    '第1-2个月：职业方向探索、技能盘点',
                    '第3-4个月：完善简历、准备基础项目',
                    '第5-6个月：实习申请与笔试练习',
                    '第7-9个月：面试准备与项目展示',
                    '第10-12个月：校招投递、补充实习经验'
                        ),
            'study_path', JSON_ARRAY(
                    '掌握岗位核心技能',
                    '完成至少1个项目输出',
                    '准备行为面试和技术面试'
                          ),
            'key_tasks', JSON_ARRAY(
                    '制作专业简历和作品集',
                    '投递岗位并跟进面试',
                    '总结面试反馈并调整策略'
                         ),
            'exam_reference', '关注校招流程：春招（2-4月）、秋招（8-10月）；部分岗位有笔试和多轮面试。',
            'risks', JSON_ARRAY(
                    '技能不够导致面试难以展开',
                    '项目经验不足影响简历竞争力',
                    '求职节奏不稳增加压力'
                     ),
            'suggestion', JSON_ARRAY(
                    '优先掌握岗位必备技能',
                    '保持作品集和项目更新',
                    '每周进行一次模拟面试'
                          )
    )
where id = 7;

update plan_template
set template_name = '就业模板2-技能强化型',
    evaluation_rules = JSON_OBJECT(
            'timeline', JSON_ARRAY(
                    '第1-2个月：明确岗位方向，补齐关键技能',
                    '第3-4个月：完成项目并完善简历',
                    '第5-6个月：投递实习，模拟笔试',
                    '第7-9个月：面试训练与项目优化',
                    '第10-12个月：跟进校招、持续技能提升'
                        ),
            'study_path', JSON_ARRAY(
                    '掌握岗位必备技能：工具→核心操作→项目实践',
                    '完成1-2个项目形成可展示成果',
                    '优化项目描述与表达能力'
                          ),
            'key_tasks', JSON_ARRAY(
                    '系统补齐技能短板',
                    '完成项目并练习讲解',
                    '模拟面试并复盘反馈'
                         ),
            'exam_reference', '关注校招及提前批岗位；技术类岗位通常有笔试和多轮面试。',
            'risks', JSON_ARRAY(
                    '技能不足导致面试难以展开',
                    '项目展示不清晰影响评分',
                    '求职节奏不稳增加压力'
                     ),
            'suggestion', JSON_ARRAY(
                    '优先学习岗位热门技能',
                    '使用STAR法提高项目表述',
                    '每周模拟面试一次'
                          )
    )
where id = 8;


update plan_template
set template_name = '就业模板3-扶持型',
    evaluation_rules = JSON_OBJECT(
            'timeline', JSON_ARRAY(
                    '第1-2个月：了解行业与岗位要求，补基础技能',
                    '第3-4个月：完成简单项目或课程作业',
                    '第5-6个月：制作简历并尝试投递小实习',
                    '第7-9个月：提升沟通、面试表达能力',
                    '第10-12个月：稳步求职并调整策略'
                        ),
            'study_path', JSON_ARRAY(
                    '从基础技能入门：工具→操作→小任务',
                    '完成至少1个可写入简历的小项目',
                    '参考优秀简历结构进行模仿优化'
                          ),
            'key_tasks', JSON_ARRAY(
                    '建立稳定学习习惯（每天1-2小时）',
                    '完成可展示项目',
                    '练习面试与沟通能力'
                         ),
            'exam_reference', '建议关注小型企业或校企合作实习机会。',
            'risks', JSON_ARRAY(
                    '基础技能薄弱导致进度慢',
                    '项目经验不足影响简历吸引力',
                    '学习节奏不稳增加压力'
                     ),
            'suggestion', JSON_ARRAY(
                    '从小任务开始积累成就感',
                    '每周复盘学习进度',
                    '寻找伙伴共同学习保持动力'
                          )
    )
where id = 9;



-- =========================
-- 4. 插入 career_suggestion
-- =========================
INSERT INTO career_suggestion (direction, career_name, description, required_skills, recommended_for)
VALUES
    ('考研', '高校教师', '从事高校教学工作，需要一定教学能力与学术研究基础。', '扎实的专业知识、学术写作能力、沟通表达', '兴趣偏学术、表达清晰、逻辑性强'),
    ('考研', '科研助理', '协助课题组完成科研实验、论文整理与科研项目推进。', '文献阅读、数据分析、实验技能', '喜欢研究、乐于探索、数学逻辑较强'),
    ('考研', '科研工程师', '在实验室或企业研发岗位进行科研开发。', '编程能力、实验操作、创新思维', '动手能力强、喜欢技术与研究'),
    ('考研', '专业领域顾问', '为企业提供专业咨询，如数据分析顾问、心理咨询顾问。', '专业能力、沟通能力、解决问题能力', '能力标签较强、有特定领域专长'),
    ('考研', '继续攻读博士', '适合科研潜力强、希望长期从事研究的人才。', '科研能力、文献阅读、英语能力', '科研兴趣浓、专业基础扎实'),
    ('考研', '实验教学助理', '负责实验教学辅助、设备管理和学生指导。', '实验技能、责任心、沟通能力', '踏实细心、基础良好'),
    ('考公', '行政管理岗', '负责日常行政事务、协调沟通和文件处理。', '组织协调能力、写作能力、责任心', '性格沉稳、擅长沟通、写作能力良好'),
    ('考公', '综合管理岗', '承担综合性文稿撰写、汇报材料整理和政策理解。', '文字表达、逻辑思维', '文笔较好、逻辑强'),
    ('考公', '纪检监察岗', '负责监督纪律执行，需要严谨作风。', '法律知识、严谨态度', '细心谨慎、公正客观'),
    ('考公', '政策研究岗', '参与政策研究与课题报告编写。', '政策分析、写作能力', '兴趣偏公共管理、逻辑能力较强'),
    ('考公', '公安/政法岗位', '具有执行力，负责治安管理与法律执行。', '执行力、纪律性、沟通能力', '性格坚毅、责任心强'),
    ('考公', '办事员/助理岗', '承担窗口服务、民生事务协调。', '服务意识、沟通能力', '亲和力强、耐心细致'),
    ('就业', 'Java 开发工程师', '负责后端开发、接口设计、数据库处理。', 'Java、SpringBoot、SQL', '逻辑性强、喜欢编程、数学能力中上'),
    ('就业', '前端工程师', '开发网页与应用界面，交互设计实现。', 'JavaScript、Vue、页面设计', '审美良好、动手能力强'),
    ('就业', '数据分析师', '使用数据工具分析业务指标。', 'Excel、Python、统计学', '喜欢数字、逻辑性强'),
    ('就业', '产品助理', '负责需求整理、文档编写、协调沟通。', '逻辑思维、沟通能力', '表达能力好、喜欢产品逻辑'),
    ('就业', '运营专员', '策划活动、管理社区、内容运营。', '文案表达、组织能力', '性格外向、有创意'),
    ('就业', '测试工程师', '负责测试用例设计，确保系统质量。', '耐心、分析能力、软件测试基础', '细心稳重、喜欢逻辑推理'),
    ('就业', '人力资源助理', '负责招聘、员工管理和培训。', '沟通能力、组织能力', '亲和、有责任心'),
    ('就业', '市场专员', '负责品牌推广与市场调研。', '营销意识、沟通能力', '外向、喜欢交流');



INSERT INTO `user_plan` (
    `user_id`,
    `template_id`,
    `user_score`,
    `generated_plan`,
    `direction`,
    `create_time`
) VALUES (
             1,
             1,
             '{"kaoyan": 82, "kaogong": 45, "jiuye": 32}',
             '# 考研个性化备考规划

         ## 一、适合考研方向的理由（结合用户得分与标签）
         从您的发展方向得分及整体倾向来看，考研是最适合您的发展路径，核心适配依据如下：

         1.  方向得分凸显核心适配性：您的考研方向得分为82分，远高于考公（45分）和就业（30分），这一显著分差表明您在考研相关的能力储备、目标匹配度或内在意愿上，均具备远超其他方向的优势。高得分意味着您更易在考研备考中保持动力、发挥潜能，降低因方向适配不足导致的中途放弃风险。

         2.  方向特性与得分逻辑匹配：考研对系统性学习能力、长期专注力和知识深度积累的要求，与您82分所体现的素质高度契合。相比之下，考公侧重时政敏感度和行政职业能力，就业侧重实践技能和职场适配度，而您在这两个方向的得分较低，说明考研更能发挥您的核心优势，是性价比最高的选择。

         ## 二、总体规划

         本次规划以"分阶段递进、公专并重、节奏可控"为核心原则，结合系统预置模板及您的方向优势，构建12个月完整备考周期，确保基础扎实、强化高效、冲刺精准。整体目标是通过科学的阶段划分和任务拆解，扎实掌握公共课与专业课知识体系，提升应试能力，成功上岸目标院校。

         1.  核心思路：遵循"公共课打基础先行，专业课逐深化跟进"的节奏，以机构主线规划为框架，融入个性化习惯培养策略，通过可量化目标和固定节奏保障备考效率。

         2.  阶段核心目标：将12个月备考周期划分为基础入门、系统强化、重点突破、冲刺收尾四个阶段，逐步实现"基础积累→能力提升→漏洞弥补→实战适配"的进阶，同时兼顾学习习惯养成与节奏把控。

         ## 三、详细时间安排

         ### （一）基础入门阶段（第1-4个月）：夯实根基，养成习惯

         1.  第1-2个月：公共课基础入门（数学+英语）

         每日学习3-4小时，采用番茄时间法（25分钟专注+5分钟休息）提升专注力。上午9:00-10:30主攻数学基础，精读教材（如同济版《高等数学》）、梳理章节框架，完成课后基础习题；下午15:00-16:30聚焦英语，背诵高频词汇（每日50个）、学习基础语法，配套简单阅读训练（每日1篇）。每周日总结本周知识点，制定下周量化目标（如"完成数学2个章节学习+掌握100个英语单词"）。

         2.  第3-4个月：专业课基础精读

         每日学习时长提升至4-5小时，固定学习时段（如上午8:30-12:00、下午14:30-17:00）形成节奏。重点研读目标院校指定专业课教材，第一遍通读梳理知识脉络，标记核心概念和重难点；第二遍精读，结合思维导图构建章节框架。每周完成1个章节的精读+框架搭建，同步整理基础笔记。公共课每日保留1小时复习时间，避免知识点遗忘。

         ### （二）系统强化阶段（第5-8个月）：深化提升，真题介入

         1.  第5-6个月：公共课第一轮强化

         每日学习5-6小时，公共课与专业课时间占比约5:5。数学主攻专题强化（如微积分、线性代数核心模块），结合机构课程梳理解题思路，刷专项习题集（如《数学基础过关660题》）；英语聚焦阅读强化，每日2-3篇真题阅读，分析文章结构和出题逻辑，积累长难句解析技巧。专业课每日2小时复习基础笔记，开展重难点初步梳理。每周完成1套公共课基础真题（节选），整理错题本。

         2.  第7-8个月：专业课真题入手+错题整理

         每日学习6小时，专业课占比提升至60%。收集目标院校近10年专业课真题，开始逐套精读，分析出题规律和高频考点，结合真题反推教材重难点；建立错题本，标注错题原因（如概念混淆、思路偏差）及对应知识点。公共课每日2.5小时，数学开展错题复盘和题型归纳，英语强化翻译和写作基础，背诵常用句式。每周日进行专业课真题复盘，总结高频考点。

         ### （三）重点突破阶段（第9-10个月）：聚焦核心，查漏补缺

         每日学习6-7小时，针对性突破薄弱环节。公共课重点攻克难点模块：数学针对高频错题题型开展专项训练，提升解题速度；英语强化写作和完形填空，每周练习2篇作文并修改，总结完形固定搭配；政治（若考）开始系统学习，结合教材和课程梳理知识点，刷基础习题。专业课深入研究真题，完成至少3套完整真题，针对真题中的薄弱章节进行教材回读，补充笔记。每周制定薄弱模块突破目标（如"攻克数学微分方程题型"）。

         ### （四）冲刺收尾阶段（第11-12个月）：模拟实战，查缺补漏

         每日学习6小时，以模拟考试和漏洞弥补为核心。按照考研实际考试时间，每周进行2-3次全科模拟考试（使用真题或权威模拟卷），适应考试节奏、把控答题时间。考后详细复盘，分析失分点，针对性补学薄弱知识点。公共课背诵政治核心考点、英语作文模板；专业课回归教材和笔记，背诵核心概念和真题答案。最后两周聚焦错题本和高频考点，避免无效刷题，保持良好应试状态。

         ## 四、学习路径建议

         ### （一）公共课学习路径：优先突破，循序渐进

         1.  数学：遵循"基础→强化→突破"路径。基础阶段（第1-2个月）精读教材、掌握公式定理和基础题型；强化阶段（第5-6个月）跟随机构课程进行专题突破，刷题巩固；重点突破阶段（第9-10个月）针对错题题型专项训练，归纳解题技巧；冲刺阶段（第11-12个月）通过模拟考试提升实战能力，复盘错题查漏补缺。

         2.  英语：以"词汇+阅读"为核心，逐步拓展。基础阶段（第1-2个月）聚焦词汇积累和基础语法，培养简单阅读能力；强化阶段（第5-6个月）主攻阅读真题，提升篇章分析和解题能力；重点突破阶段（第9-10个月）强化翻译、写作和完形，积累模板和固定搭配；冲刺阶段（第11-12个月）模拟考试打磨答题节奏，背诵作文模板并仿写。

         3.  政治（若考）：晚启动重效率。第7个月开始系统学习，遵循"教材+课程→刷题→背诵"路径，基础阶段梳理知识点框架，强化阶段刷真题选择题，冲刺阶段背诵核心考点和主观题模板。

         ### （二）专业课学习路径：三步走策略，真题为核心

         严格执行"教材→重难点→真题"三步走策略。基础阶段（第3-4个月）精读2遍教材，构建知识框架，整理基础笔记；强化阶段（第7-8个月）结合真题梳理重难点，标记高频考点，开展错题整理；重点突破阶段（第9-10个月）深入研究真题，回读教材弥补薄弱环节；冲刺阶段（第11-12个月）回归笔记和真题，背诵核心内容，通过模拟考试检验学习效果。

         ### （三）整体节奏把控：跟随机构主线，个性化调整

         以专业考研机构的主线规划为基础框架，同步结合自身学习进度调整。每周日根据机构规划和自身掌握情况，制定下一周可量化小目标（如"完成数学1个专题学习+2篇英语阅读+1章专业课复习"），每日固定时间学习形成规律节奏，确保整体进度不滞后。

         ## 五、考试日期参考

         考研考试时间通常为每年12月的最后一个周末，具体日期以当年教育部官方通知为准，参考时间安排如下（以周六、周日为例）：

         1.  第一天

         上午（8:30-11:30）：思想政治理论/管理类联考综合能力（若考）

         下午（14:00-17:00）：外国语（英语一/英语二）

         2.  第二天

         上午（8:30-11:30）：业务课一（数学/专业课一，若考）

         下午（14:00-17:00）：业务课二（专业课二）

         3.  关键时间节点参考

         上年9月：教育部公布当年考研大纲及招生简章

         上年10月：全国硕士研究生招生考试网上报名（正式报名阶段）

         上年11月：考研报名信息现场确认/网上确认

         上年12月：考前10天左右打印准考证，月底参加考试

         次年2月：公布考试成绩

         次年3月：公布国家线及目标院校复试分数线，陆续开展复试

         次年4月：公布复试结果，发放录取通知书

         ## 六、风险点与建议

         ### （一）核心风险点分析

         1.  自律不足导致计划落地困难：备考周期长，若缺乏有效自我约束，易出现拖延、学习效率低下等问题，导致阶段目标无法完成，整体进度滞后。

         2.  公共课基础薄弱引发焦虑：数学、英语等公共课基础若不扎实，在强化阶段易出现知识点衔接困难，进而产生备考焦虑，影响学习心态。

         3.  节奏把控不当延误复习进程：若基础阶段耗时过长，或强化阶段刷题效率低，会导致冲刺阶段时间不足，无法完成模拟考试和查缺补漏，影响应试效果。

         ### （二）针对性解决建议

         1.  强化自律与专注力：采用番茄时间法（25分钟专注学习+5分钟休息）提升单位时间效率；建立"每日任务清单"，完成一项打勾确认，每周复盘任务完成情况，未完成项分析原因并补学；固定每日学习时段（如上午8:30-12:00、下午14:30-18:00），形成条件反射式学习节奏。

         2.  夯实公共课基础，缓解焦虑：基础阶段（第1-4个月）集中精力攻克公共课基础，数学逐章精读教材、吃透基础习题，英语坚持每日背词和语法学习；若基础薄弱，可提前1-2个月启动备考，或选择基础类课程辅助学习；定期通过小测试检验基础掌握情况，及时发现漏洞并弥补，避免焦虑积累。

         3.  科学把控备考节奏：以系统模板的阶段划分为基准，每周制定可量化小目标（如"数学完成20道基础题+英语背50个单词+专业课精读1节"），每月末进行进度复盘，若滞后及时调整后续计划（如压缩休息时间、聚焦核心知识点）；强化阶段避免"盲目刷题"，注重错题复盘和题型归纳，提升刷题效率，为冲刺阶段预留充足时间。

         4.  院校选择辅助建议：结合参考信息，优先选择专业课难度中等的目标院校，降低备考压力；提前收集目标院校近3年录取线、报录比及复试规则，明确备考分数目标，增强学习针对性。',
             '考研',
             '2025-11-23 19:53:11'
         );


INSERT INTO user_direction(user_id, preferred_direction, system_direction, final_direction, is_conflict, confirm_status, create_time, update_time)
VALUE (1, '考研', '考研', '考研', 0, 1, '2025-11-23 19:59:13', '2025-11-23 19:59:13');




-- 禁用外键约束检查
SET FOREIGN_KEY_CHECKS = 0;

-- 删除question表
DROP TABLE IF EXISTS question;

-- （可选）恢复后重新启用外键检查
SET FOREIGN_KEY_CHECKS = 1;





-- --------------------------------------------------
-- 兴趣测评问卷（选择题 1）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '在学习或工作中，你更愿意投入时间在：',
           1,
           '[
             {"key":"A","label":"深入研究某一具体知识点","tags":["深入研究"],"weights":{"kaoyan":5,"kaogong":1,"jiuye":1},"interest": {"R": 0, "I": 3, "A": 0, "S": 0, "E": 0, "C": 1}},
             {"key":"B","label":"创造性解决问题或做项目","tags":["创新"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":4},"interest": {"R": 1, "I": 1, "A": 3, "S": 0, "E": 0, "C": 0}},
             {"key":"C","label":"团队协作与沟通","tags":["合作沟通"],"weights":{"kaoyan":0,"kaogong":3,"jiuye":3},"interest": {"R": 0, "I": 0, "A": 0, "S": 3, "E": 1, "C": 0}},
             {"key":"D","label":"结构化分析、归纳整理","tags":["逻辑结构"],"weights":{"kaoyan":3,"kaogong":3,"jiuye":3}, "interest": {"R": 1, "I": 2, "A": 0, "S": 0, "E": 0, "C": 3}}
           ]',
           1
       );

-- --------------------------------------------------
-- 兴趣测评问卷（选择题 2）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你更喜欢以下哪种学习场景？',
           1,
           '[
             {"key":"A","label":"不断探索学术问题、查阅文献","tags":["学术探索"],"weights":{"kaoyan":5,"kaogong":1,"jiuye":1},"interest": {"R": 0, "I": 3, "A": 0, "S": 0, "E": 0, "C": 1}},
             {"key":"B","label":"实践中动手尝试、做项目","tags":["动手实践"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5},"interest": {"R": 3, "I": 1, "A": 1, "S": 0, "E": 0, "C": 0}},
             {"key":"C","label":"在规则明确的任务中执行流程","tags":["流程执行"],"weights":{"kaoyan":0,"kaogong":5,"jiuye":2},"interest": {"R": 1, "I": 0, "A": 0, "S": 0, "E": 0, "C": 3}},
             {"key":"D","label":"探索跨领域知识","tags":["跨领域"],"weights":{"kaoyan":3,"kaogong":1,"jiuye":3},"interest": {"R": 0, "I": 2, "A": 2, "S": 0, "E": 0, "C": 1}}
           ]',
           2
       );

-- --------------------------------------------------
-- 兴趣测评问卷（选择题 3）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '面对一个复杂问题，你通常会：',
           1,
           '[
             {"key":"A","label":"拆解步骤并逐项验证","tags":["逻辑拆解"],"weights":{"kaoyan":4,"kaogong":4,"jiuye":3},"interest": {"R": 1, "I": 2, "A": 0, "S": 0, "E": 0, "C": 3}},
             {"key":"B","label":"寻找创新角度突破","tags":["创新"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5},"interest": {"R": 1, "I": 1, "A": 3, "S": 0, "E": 0, "C": 0}},
             {"key":"C","label":"与别人讨论后得出结论","tags":["沟通协作"],"weights":{"kaoyan":1,"kaogong":3,"jiuye":3},"interest": {"R": 0, "I": 0, "A": 0, "S": 3, "E": 1, "C": 0}},
             {"key":"D","label":"查阅已有资料并深挖","tags":["查阅资料"],"weights":{"kaoyan":5,"kaogong":1,"jiuye":1},"interest": {"R": 0, "I": 3, "A": 0, "S": 0, "E": 0, "C": 1}}
           ]',
           3
       );

-- --------------------------------------------------
-- 兴趣测评问卷（选择题 4）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你对未来职业的期待更偏向：',
           1,
           '[
             {"key":"A","label":"稳定、规则清晰","tags":["稳定"],"weights":{"kaoyan":1,"kaogong":5,"jiuye":1},"interest": {"R": 1, "I": 0, "A": 0, "S": 0, "E": 0, "C": 3}},
             {"key":"B","label":"挑战性强、能不断提升","tags":["挑战"],"weights":{"kaoyan":4,"kaogong":2,"jiuye":3},"interest": {"R": 1, "I": 2, "A": 1, "S": 0, "E": 1, "C": 0}},
             {"key":"C","label":"注重创新和自由度","tags":["创新自由"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5},"interest": {"R": 0, "I": 1, "A": 3, "S": 0, "E": 0, "C": 0}},
             {"key":"D","label":"倾向于继续深造或探索","tags":["继续深造"],"weights":{"kaoyan":5,"kaogong":1,"jiuye":1},"interest": {"R": 0, "I": 3, "A": 0, "S": 0, "E": 0, "C": 1}}
           ]',
           4
       );

-- --------------------------------------------------
-- 兴趣测评问卷（选择题 5）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你喜欢处理哪种类型的任务？',
           1,
           '[
             {"key":"A","label":"需要持续思考的理论问题","tags":["理论思考"],"weights":{"kaoyan":5,"kaogong":1,"jiuye":1},"interest": {"R": 0, "I": 3, "A": 0, "S": 0, "E": 0, "C": 1}},
             {"key":"B","label":"需要大量实践的任务","tags":["实践型"],"weights":{"kaoyan":2,"kaogong":0,"jiuye":5},"interest": {"R": 3, "I": 1, "A": 1, "S": 0, "E": 0, "C": 0}},
             {"key":"C","label":"具有规范流程的行政事务","tags":["行政流程"],"weights":{"kaoyan":0,"kaogong":5,"jiuye":2},"interest": {"R": 1, "I": 0, "A": 0, "S": 0, "E": 0, "C": 3}},
             {"key":"D","label":"需要沟通协调的事务","tags":["沟通"],"weights":{"kaoyan":1,"kaogong":4,"jiuye":3}, "interest": {"R": 0, "I": 0, "A": 0, "S": 3, "E": 1, "C": 0}}
           ]',
           5
       );

-- --------------------------------------------------
-- 兴趣测评问卷（选择题 6）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '在学习过程中，你最有动力的是哪一种方式？',
           1,
           '[
             {
               "key": "A",
               "label": "系统化学习，有明确的知识体系",
               "tags": ["系统学习"],
               "weights": {"kaoyan": 5, "kaogong": 2, "jiuye": 2},
               "interest": {"R": 0, "I": 3, "A": 0, "S": 0, "E": 0, "C": 2}
             },
             {
               "key": "B",
               "label": "在实践或项目中边做边学",
               "tags": ["项目实践"],
               "weights": {"kaoyan": 2, "kaogong": 0, "jiuye": 5},
               "interest": {"R": 3, "I": 1, "A": 1, "S": 0, "E": 0, "C": 0}
             },
             {
               "key": "C",
               "label": "在团队交流和互相启发中学习",
               "tags": ["讨论学习"],
               "weights": {"kaoyan": 1, "kaogong": 3, "jiuye": 3},
               "interest": {"R": 0, "I": 0, "A": 0, "S": 3, "E": 1, "C": 0}
             },
             {
               "key": "D",
               "label": "通过大量查询资料、自主探索",
               "tags": ["自主探索"],
               "weights": {"kaoyan": 5, "kaogong": 1, "jiuye": 1},
               "interest": {"R": 0, "I": 3, "A": 1, "S": 0, "E": 0, "C": 1}
             }
           ]
           ',
           6
       );

-- --------------------------------------------------
-- 兴趣测评问卷（选择题 7）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '当你面对一项新的任务时，你通常最关注什么？',
           1,
           '[
             {
               "key": "A",
               "label": "任务的底层原理和逻辑是什么",
               "tags": ["逻辑原理"],
               "weights": {"kaoyan": 5, "kaogong": 2, "jiuye": 2},
               "interest": {"R": 0, "I": 3, "A": 0, "S": 0, "E": 0, "C": 2}
             },
             {
               "key": "B",
               "label": "任务是否可以通过创新方式完成",
               "tags": ["创新尝试"],
               "weights": {"kaoyan": 2, "kaogong": 0, "jiuye": 5},
               "interest": {"R": 1, "I": 1, "A": 3, "S": 0, "E": 0, "C": 0}
             },
             {
               "key": "C",
               "label": "任务中需要和谁沟通、如何协作",
               "tags": ["沟通协作"],
               "weights": {"kaoyan": 1, "kaogong": 4, "jiuye": 3},
               "interest": {"R": 0, "I": 0, "A": 0, "S": 3, "E": 1, "C": 0}
             },
             {
               "key": "D",
               "label": "任务的要求是否明确、流程是否规范",
               "tags": ["流程规范"],
               "weights": {"kaoyan": 0, "kaogong": 5, "jiuye": 2},
               "interest": {"R": 1, "I": 0, "A": 0, "S": 0, "E": 0, "C": 3}
             }
           ]
           ',
           7
       );

-- --------------------------------------------------
-- 文本题（兴趣测评 1）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '请描述你最感兴趣的学习主题或活动是什么？',
           3,
           8
       );

-- --------------------------------------------------
-- 文本题（兴趣测评 2）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='兴趣测评问卷'),
           '你认为哪些事情会让你产生强烈的投入感？请举例说明。',
           3,
           9
       );



-- --------------------------------------------------
-- 能力测评问卷（选择题 1）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '面对一个全新的任务，你通常能够：',
           1,
           '[
             {"key":"A","label":"快速理解需求并拆解步骤","tags":["需求拆解"],"weights":{"kaoyan":4,"kaogong":4,"jiuye":4},
               "ability":{"learning":2,"analysis":2,"communication":0,"stress":0,"discipline":1}},
             {"key":"B","label":"需要时间适应，但最终能独立完成","tags":["适应力"],"weights":{"kaoyan":3,"kaogong":2,"jiuye":3},
               "ability":{"learning":1,"analysis":1,"communication":0,"stress":1,"discipline":1}},
             {"key":"C","label":"更倾向在别人指导下推进","tags":["被指导型"],"weights":{"kaoyan":1,"kaogong":3,"jiuye":2},
               "ability":{"learning":0,"analysis":0,"communication":1,"stress":1,"discipline":1}},
             {"key":"D","label":"希望按明确模板完成任务","tags":["模板依赖"],"weights":{"kaoyan":0,"kaogong":5,"jiuye":2},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":2}}
           ]
           ',
           1
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 2）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你认为自己在逻辑分析方面的能力属于：',
           1,
           '[
             {"key":"A","label":"能够处理复杂逻辑，且乐于推理","tags":["强逻辑"],"weights":{"kaoyan":5,"kaogong":4,"jiuye":3},
               "ability":{"learning":1,"analysis":2,"communication":0,"stress":0,"discipline":0}},
             {"key":"B","label":"逻辑较好，能结构化处理信息","tags":["中等逻辑"],"weights":{"kaoyan":4,"kaogong":3,"jiuye":3},
               "ability":{"learning":1,"analysis":1,"communication":0,"stress":0,"discipline":1}},
             {"key":"C","label":"偶尔混乱，但努力能做好","tags":["一般逻辑"],"weights":{"kaoyan":2,"kaogong":2,"jiuye":3},
               "ability":{"learning":1,"analysis":1,"communication":0,"stress":1,"discipline":1}},
             {"key":"D","label":"更擅长凭直觉判断","tags":["直觉型"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":4},
               "ability":{"learning":0,"analysis":0,"communication":1,"stress":0,"discipline":0}}
           ]
           ',
           2
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 3）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '在团队协作中，你的角色更像是：',
           1,
           '[
             {"key":"A","label":"负责整体规划的组织者","tags":["组织规划"],"weights":{"kaoyan":3,"kaogong":4,"jiuye":4},
               "ability":{"learning":1,"analysis":1,"communication":1,"stress":0,"discipline":1}},
             {"key":"B","label":"提出想法的创意贡献者","tags":["创新者"],"weights":{"kaoyan":2,"kaogong":1,"jiuye":5},
               "ability":{"learning":1,"analysis":1,"communication":1,"stress":0,"discipline":0}},
             {"key":"C","label":"按时完成分工任务的执行者","tags":["执行者"],"weights":{"kaoyan":1,"kaogong":5,"jiuye":3},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":2}},
             {"key":"D","label":"协调沟通双方的调和者","tags":["沟通者"],"weights":{"kaoyan":1,"kaogong":4,"jiuye":4},
               "ability":{"learning":0,"analysis":0,"communication":2,"stress":1,"discipline":1}}
           ]
           ',
           3
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 4）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你处理压力的能力是：',
           1,
           '[
             {"key":"A","label":"压力越大表现越好","tags":["抗压强"],"weights":{"kaoyan":4,"kaogong":4,"jiuye":5},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":2,"discipline":1}},
             {"key":"B","label":"压力中能保持稳定","tags":["稳定型"],"weights":{"kaoyan":3,"kaogong":4,"jiuye":3},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":2,"discipline":0}},
             {"key":"C","label":"偶尔会焦虑，但能完成任务","tags":["轻度焦虑"],"weights":{"kaoyan":2,"kaogong":3,"jiuye":2},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":1}},
             {"key":"D","label":"压力强时效率明显下降","tags":["抗压弱"],"weights":{"kaoyan":1,"kaogong":1,"jiuye":1},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":0,"discipline":0}}
           ]
           ',
           4
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 5）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '当你需要持续学习某个知识体系时：',
           1,
           '[
             {"key":"A","label":"能长期坚持并保持兴趣","tags":["持续学习"],"weights":{"kaoyan":5,"kaogong":2,"jiuye":3},
               "ability":{"learning":2,"analysis":0,"communication":0,"stress":1,"discipline":2}},
             {"key":"B","label":"有时会松懈，但整体能坚持","tags":["中度坚持"],"weights":{"kaoyan":3,"kaogong":3,"jiuye":3},
               "ability":{"learning":1,"analysis":0,"communication":0,"stress":1,"discipline":1}},
             {"key":"C","label":"容易失去动力","tags":["动力弱"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":2},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":0}},
             {"key":"D","label":"更喜欢任务型学习","tags":["任务驱动"],"weights":{"kaoyan":2,"kaogong":5,"jiuye":3},
               "ability":{"learning":1,"analysis":0,"communication":0,"stress":0,"discipline":2}}
           ]
           ',
           5
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 6）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你对文字阅读、材料理解的能力属于：',
           1,
           '[
             {"key":"A","label":"能快速抓住关键点","tags":["阅读强"],"weights":{"kaoyan":5,"kaogong":3,"jiuye":3},
               "ability":{"learning":2,"analysis":1,"communication":0,"stress":0,"discipline":1}},
             {"key":"B","label":"阅读速度一般，但理解较好","tags":["阅读中等"],"weights":{"kaoyan":4,"kaogong":3,"jiuye":3},
               "ability":{"learning":1,"analysis":1,"communication":0,"stress":0,"discipline":1}},
             {"key":"C","label":"理解需要反复阅读","tags":["阅读弱"],"weights":{"kaoyan":2,"kaogong":2,"jiuye":2},
               "ability":{"learning":1,"analysis":0,"communication":0,"stress":1,"discipline":0}},
             {"key":"D","label":"阅读枯燥内容时容易走神","tags":["注意力弱"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":3},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":0}}
           ]
           ',
           6
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 7）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你在解决问题时通常采取的策略是：',
           1,
           '[
             {"key":"A","label":"按逻辑推演，避免遗漏","tags":["系统推演"],"weights":{"kaoyan":5,"kaogong":4,"jiuye":3},
               "ability":{"learning":1,"analysis":2,"communication":0,"stress":0,"discipline":1}},
             {"key":"B","label":"尽快找到突破口再优化","tags":["突破口优先"],"weights":{"kaoyan":3,"kaogong":3,"jiuye":4},
               "ability":{"learning":1,"analysis":1,"communication":0,"stress":1,"discipline":1}},
             {"key":"C","label":"先问别人经验再实践","tags":["借鉴经验"],"weights":{"kaoyan":2,"kaogong":3,"jiuye":3},
               "ability":{"learning":1,"analysis":0,"communication":1,"stress":0,"discipline":1}},
             {"key":"D","label":"凭感觉尝试多种方案","tags":["试错型"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":4},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":0}}
           ]
           ',
           7
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 8）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你处理数字、数据类任务的能力属于：',
           1,
           '[
             {"key":"A","label":"非常擅长，能快速分析","tags":["数据强"],"weights":{"kaoyan":5,"kaogong":3,"jiuye":4},
               "ability":{"learning":1,"analysis":2,"communication":0,"stress":0,"discipline":1}},
             {"key":"B","label":"一般，但能完成","tags":["数据中等"],"weights":{"kaoyan":3,"kaogong":3,"jiuye":3},
               "ability":{"learning":1,"analysis":1,"communication":0,"stress":0,"discipline":1}},
             {"key":"C","label":"数字类任务会让我紧张","tags":["数据弱"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":1},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":0}},
             {"key":"D","label":"更喜欢语言或创造类任务","tags":["偏文科"],"weights":{"kaoyan":2,"kaogong":2,"jiuye":4},
               "ability":{"learning":0,"analysis":0,"communication":1,"stress":0,"discipline":0}}
           ]
           ',
           8
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 9）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '面对必须短时间完成的任务，你的表现通常是：',
           1,
           '[
             {"key":"A","label":"能在短时间集中精力高效完成","tags":["短时高效"],"weights":{"kaoyan":4,"kaogong":4,"jiuye":5},
               "ability":{"learning":0,"analysis":1,"communication":0,"stress":2,"discipline":2}},
             {"key":"B","label":"能完成，但会比较紧张","tags":["短时紧张"],"weights":{"kaoyan":3,"kaogong":3,"jiuye":2},
               "ability":{"learning":0,"analysis":1,"communication":0,"stress":1,"discipline":1}},
             {"key":"C","label":"效率下降明显","tags":["短时低效"],"weights":{"kaoyan":1,"kaogong":2,"jiuye":1},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":0,"discipline":0}},
             {"key":"D","label":"需要一定准备时间","tags":["需准备"],"weights":{"kaoyan":2,"kaogong":3,"jiuye":2},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":1}}
           ]
           ',
           9
       );

-- --------------------------------------------------
-- 能力测评问卷（选择题 10）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '你在面对“规则、流程、制度类”任务的适应度：',
           1,
           '[
             {"key":"A","label":"非常适应，执行力强","tags":["流程执行高"],"weights":{"kaoyan":1,"kaogong":5,"jiuye":3},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":2}},
             {"key":"B","label":"适应良好","tags":["流程中等"],"weights":{"kaoyan":2,"kaogong":4,"jiuye":3},
               "ability":{"learning":0,"analysis":0,"communication":0,"stress":1,"discipline":1}},
             {"key":"C","label":"能够完成但不喜欢","tags":["流程抵触"],"weights":{"kaoyan":3,"kaogong":2,"jiuye":3},
               "ability":{"learning":1,"analysis":0,"communication":0,"stress":1,"discipline":0}},
             {"key":"D","label":"更倾向自由度高的方式","tags":["自由偏好"],"weights":{"kaoyan":4,"kaogong":1,"jiuye":5},
               "ability":{"learning":1,"analysis":0,"communication":1,"stress":0,"discipline":0}}
           ]
           ',
           10
       );

-- --------------------------------------------------
-- 文本题（能力测评 1）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '请描述你认为自己最突出的能力是什么？',
           3,
           11
       );

-- --------------------------------------------------
-- 文本题（能力测评 2）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='能力测评问卷'),
           '谈谈你觉得自己在哪些方面能力不足？在哪种情境下最容易暴露？',
           3,
           12
       );


-- --------------------------------------------------
-- 自我评价问卷（选择题 1）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你认为自己当前的发展目标清晰度如何？',
           1,
           '[
             {"key":"A","label":"非常清晰，方向明确","tags":["目标清晰"],
               "weights":{"kaoyan":4,"kaogong":4,"jiuye":4},
               "self_evaluation":{"major_interest":4}},
             {"key":"B","label":"大致方向明确，但细节不清楚","tags":["方向模糊"],
               "weights":{"kaoyan":3,"kaogong":3,"jiuye":3},
               "self_evaluation":{"major_interest":3}},
             {"key":"C","label":"比较模糊，需要探索","tags":["需探索"],
               "weights":{"kaoyan":2,"kaogong":2,"jiuye":2},
               "self_evaluation":{"major_interest":2}},
             {"key":"D","label":"完全没有方向","tags":["无方向"],
               "weights":{"kaoyan":1,"kaogong":1,"jiuye":1},
               "self_evaluation":{"major_interest":1}}
           ]
           ',
           1
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 2）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你对自己未来三年的规划程度：',
           1,
           '[
             {"key":"A","label":"有明确规划并已在执行","tags":["规划明确"],
               "weights":{"kaoyan":4,"kaogong":4,"jiuye":4},
               "self_evaluation":{"major_interest":4}},
             {"key":"B","label":"有大体设想","tags":["规划初步"],
               "weights":{"kaoyan":3,"kaogong":2,"jiuye":3},
               "self_evaluation":{"major_interest":3}},
             {"key":"C","label":"经常变化，没有定型","tags":["规划不稳定"],
               "weights":{"kaoyan":2,"kaogong":3,"jiuye":2},
               "self_evaluation":{"major_interest":2}},
             {"key":"D","label":"没有想过","tags":["无规划"],
               "weights":{"kaoyan":1,"kaogong":1,"jiuye":1},
               "self_evaluation":{"major_interest":1}}
           ]
           ',
           2
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 3）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你对自己学习能力的自我评价是：',
           1,
           '[
             {"key":"A","label":"学习能力强，能快速掌握难点","tags":["学习强"],
               "weights":{"kaoyan":5,"kaogong":3,"jiuye":3},
               "self_evaluation":{"postgraduate_intent":4}},
             {"key":"B","label":"学习能力不错","tags":["学习中等"],
               "weights":{"kaoyan":3,"kaogong":3,"jiuye":3},
               "self_evaluation":{"postgraduate_intent":3}},
             {"key":"C","label":"需要时间消化","tags":["学习慢热"],
               "weights":{"kaoyan":2,"kaogong":2,"jiuye":2},
               "self_evaluation":{"postgraduate_intent":2}},
             {"key":"D","label":"学习困难，需要大量辅导","tags":["学习弱"],
               "weights":{"kaoyan":1,"kaogong":1,"jiuye":1},
               "self_evaluation":{"postgraduate_intent":1}}
           ]
           ',
           3
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 4）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你对当前专业的匹配度评价如何？',
           1,
           '[
             {"key":"A","label":"非常适合，我很喜欢","tags":["专业高度匹配"],
               "weights":{"kaoyan":5,"kaogong":2,"jiuye":3},
               "self_evaluation":{"major_interest":4}},
             {"key":"B","label":"比较适合","tags":["专业中度匹配"],
               "weights":{"kaoyan":3,"kaogong":3,"jiuye":3},
               "self_evaluation":{"major_interest":3}},
             {"key":"C","label":"兴趣一般","tags":["兴趣一般"],
               "weights":{"kaoyan":2,"kaogong":3,"jiuye":2},
               "self_evaluation":{"major_interest":2}},
             {"key":"D","label":"不适合，想转方向","tags":["专业不匹配"],
               "weights":{"kaoyan":1,"kaogong":4,"jiuye":4},
               "self_evaluation":{"major_interest":1}}
           ]
           ',
           4
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 5）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你认为自己当前最需要提升的方面是：',
           1,
           '[
             {"key":"A","label":"学习/科研能力","tags":["需提升学习"],
               "weights":{"kaoyan":3,"kaogong":1,"jiuye":2},
               "self_evaluation":{"job_intent":2}},
             {"key":"B","label":"沟通表达","tags":["需提升沟通"],
               "weights":{"kaoyan":1,"kaogong":3,"jiuye":4},
               "self_evaluation":{"job_intent":4}},
             {"key":"C","label":"执行力和规划能力","tags":["需提升执行"],
               "weights":{"kaoyan":2,"kaogong":4,"jiuye":3},
               "self_evaluation":{"job_intent":3}},
             {"key":"D","label":"自信心与稳定性","tags":["需提升自信"],
               "weights":{"kaoyan":2,"kaogong":2,"jiuye":2},
               "self_evaluation":{"job_intent":1}}
           ]
           ',
           5
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 6）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '面对长期坚持的任务（如准备考试），你的自我评价是：',
           1,
           '[
             {"key":"A","label":"非常自律，能长期坚持","tags":["高度自律"],
               "weights":{"kaoyan":5,"kaogong":3,"jiuye":4},
               "self_evaluation":{"exam_patience":4}},
             {"key":"B","label":"大部分时候能坚持","tags":["较自律"],
               "weights":{"kaoyan":3,"kaogong":3,"jiuye":3},
               "self_evaluation":{"exam_patience":3}},
             {"key":"C","label":"时常中断","tags":["自律弱"],
               "weights":{"kaoyan":2,"kaogong":2,"jiuye":2},
               "self_evaluation":{"exam_patience":2}},
             {"key":"D","label":"难以维持长期节奏","tags":["不自律"],
               "weights":{"kaoyan":1,"kaogong":1,"jiuye":1},
               "self_evaluation":{"exam_patience":1}}
           ]
           ',
           6
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 7）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你对压力的感受属于哪种？',
           1,
           '[
             {"key":"A","label":"压力激发我的动力","tags":["正性压力"],
               "weights":{"kaoyan":4,"kaogong":3,"jiuye":5},
               "self_evaluation":{"civil_intent":4}},
             {"key":"B","label":"压力下能保持正常","tags":["稳定压力"],
               "weights":{"kaoyan":3,"kaogong":3,"jiuye":3},
               "self_evaluation":{"civil_intent":3}},
             {"key":"C","label":"压力会稍微影响发挥","tags":["轻度影响"],
               "weights":{"kaoyan":2,"kaogong":2,"jiuye":2},
               "self_evaluation":{"civil_intent":2}},
             {"key":"D","label":"压力会让我效率下降","tags":["压力敏感"],
               "weights":{"kaoyan":1,"kaogong":1,"jiuye":1},
               "self_evaluation":{"civil_intent":1}}
           ]
           ',
           7
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 8）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '在未知领域，你通常会：',
           1,
           '[
             {"key":"A","label":"主动探索并尝试突破","tags":["主动探索"],
               "weights":{"kaoyan":4,"kaogong":3,"jiuye":4},
               "self_evaluation":{"job_intent":4}},
             {"key":"B","label":"逐步了解后再行动","tags":["稳健探索"],
               "weights":{"kaoyan":3,"kaogong":4,"jiuye":3},
               "self_evaluation":{"job_intent":3}},
             {"key":"C","label":"害怕尝试新事物","tags":["畏难"],
               "weights":{"kaoyan":1,"kaogong":2,"jiuye":2},
               "self_evaluation":{"job_intent":2}},
             {"key":"D","label":"需要别人带动","tags":["被动型"],
               "weights":{"kaoyan":1,"kaogong":3,"jiuye":2},
               "self_evaluation":{"job_intent":1}}
           ]
           ',
           8
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 9）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你对生活、学习、未来的不确定性接受度如何？',
           1,
           '[
             {"key":"A","label":"能接受不确定性，甚至喜欢挑战","tags":["高不确定性接受"],
               "weights":{"kaoyan":3,"kaogong":1,"jiuye":5},
               "self_evaluation":{"civil_intent":1}},
             {"key":"B","label":"可以接受，但希望有一定规划","tags":["中等接受"],
               "weights":{"kaoyan":4,"kaogong":3,"jiuye":3},
               "self_evaluation":{"civil_intent":2}},
             {"key":"C","label":"不太喜欢不确定性","tags":["低接受"],
               "weights":{"kaoyan":2,"kaogong":4,"jiuye":2},
               "self_evaluation":{"civil_intent":3}},
             {"key":"D","label":"非常不喜欢不确定性","tags":["极低接受"],
               "weights":{"kaoyan":1,"kaogong":5,"jiuye":1},
               "self_evaluation":{"civil_intent":4}}
           ]
           ',
           9
       );

-- --------------------------------------------------
-- 自我评价问卷（选择题 10）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, options, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你觉得目前的心理状态更接近以下哪种？',
           1,
           '[
             {"key":"A","label":"积极、充满动力","tags":["心理积极"],
               "weights":{"kaoyan":3,"kaogong":3,"jiuye":5},
               "self_evaluation":{"exam_patience":4}},
             {"key":"B","label":"稳定、平稳","tags":["心理稳定"],
               "weights":{"kaoyan":4,"kaogong":4,"jiuye":3},
               "self_evaluation":{"exam_patience":3}},
             {"key":"C","label":"偶有焦虑","tags":["轻焦虑"],
               "weights":{"kaoyan":2,"kaogong":3,"jiuye":2},
               "self_evaluation":{"exam_patience":2}},
             {"key":"D","label":"经常感到迷茫","tags":["迷茫"],
               "weights":{"kaoyan":1,"kaogong":2,"jiuye":1},
               "self_evaluation":{"exam_patience":1}}
           ]
           ',
           10
       );

-- --------------------------------------------------
-- 文本题（自我评价 1）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '请描述你对自己当前状态的整体评价，包括学习、生活、规划等方面。',
           3,
           11
       );

-- --------------------------------------------------
-- 文本题（自我评价 2）
-- --------------------------------------------------
INSERT INTO question (questionnaire_id, content, type, order_num)
VALUES (
           (SELECT id FROM questionnaire WHERE title='自我评价问卷'),
           '你认为自己未来最想成为什么样的人？请描述原因。',
           3,
           12
       );




-- 清空task表（可选，测试环境使用）
-- TRUNCATE TABLE task;

-- 插入mock数据（满足每个用户daily/exam/plan分类至少2个任务）
INSERT INTO `task` (
    `user_id`, `category`, `content`, `priority`, `due_date`, `complete_time`, `status`, `create_time`, `update_time`, `is_delete`
) VALUES
-- ========== user_id=1（考研） ==========
-- daily（日常学习）：3个任务
(1, 'daily', '背诵50个考研英语大纲词汇并复习昨日单词', 2, '2025-12-15', '2025-12-15 08:20:00', 1, '2025-12-15 07:00:00', '2025-12-15 08:20:00', 0),
(1, 'daily', '做考研英语阅读真题2篇并分析错题', 2, '2025-12-16', '2025-12-16 09:00:00', 1, '2025-12-16 07:30:00', '2025-12-16 09:00:00', 0),
(1, 'daily', '背诵考研政治马原核心考点10条', 2, '2025-12-17', NULL, 0, '2025-12-17 07:00:00', '2025-12-17 07:00:00', 0),
-- exam（备考任务）：3个任务
(1, 'exam', '复习高数第三章 - 微分方程知识点及例题', 1, '2025-12-20', '2025-12-18 15:30:00', 1, '2025-12-10 09:00:00', '2025-12-18 15:30:00', 0),
(1, 'exam', '整理数据结构二叉树题型及解题思路', 1, '2025-12-25', NULL, 0, '2025-12-12 10:00:00', '2025-12-12 10:00:00', 0),
(1, 'exam', '做考研专业课《数据结构》真题一套', 1, '2025-12-28', NULL, 0, '2025-12-14 09:00:00', '2025-12-14 09:00:00', 0),
-- plan（规划任务）：2个任务
(1, 'plan', '制定考研政治最后冲刺阶段复习计划', 2, '2025-12-18', NULL, 0, '2025-12-10 14:00:00', '2025-12-10 14:00:00', 0),
(1, 'plan', '规划考研英语最后一个月真题刷题计划', 2, '2025-12-20', '2025-12-19 10:00:00', 1, '2025-12-11 14:00:00', '2025-12-19 10:00:00', 0),
-- other（其他事项）：1个任务
(1, 'other', '购买考研专用2B铅笔和橡皮套装', 3, '2025-12-22', NULL, 0, '2025-12-11 16:00:00', '2025-12-11 16:00:00', 0),

-- ========== user_id=2（考公） ==========
-- daily（日常学习）：2个任务
(2, 'daily', '背诵申论经典范文段落并默写', 2, '2025-12-16', '2025-12-16 10:00:00', 1, '2025-12-16 08:00:00', '2025-12-16 10:00:00', 0),
(2, 'daily', '听行测判断推理网课2节并整理笔记', 2, '2025-12-17', '2025-12-17 15:00:00', 1, '2025-12-17 09:00:00', '2025-12-17 15:00:00', 0),
-- exam（备考任务）：3个任务
(2, 'exam', '刷行测数量关系模块100道真题', 1, '2025-12-20', '2025-12-19 19:00:00', 1, '2025-12-10 09:30:00', '2025-12-19 19:00:00', 0),
(2, 'exam', '完成申论真题一套（限时3小时）', 1, '2025-12-25', NULL, 0, '2025-12-12 10:00:00', '2025-12-12 10:00:00', 0),
(2, 'exam', '刷行测资料分析模块80道真题', 1, '2025-12-22', '2025-12-21 18:00:00', 1, '2025-12-11 09:00:00', '2025-12-21 18:00:00', 0),
-- plan（规划任务）：2个任务
(2, 'plan', '报名国考线下模考班', 2, '2025-12-28', NULL, 0, '2025-12-12 11:00:00', '2025-12-12 11:00:00', 0),
(2, 'plan', '整理考公时政热点（近3个月）', 2, '2025-12-24', NULL, 0, '2025-12-13 15:00:00', '2025-12-13 15:00:00', 0),
-- other（其他事项）：1个任务
(2, 'other', '打印公务员考试报名登记表', 3, '2025-12-25', NULL, 0, '2025-12-13 15:00:00', '2025-12-13 15:00:00', 0),

-- ========== user_id=3（就业） ==========
-- daily（日常学习）：2个任务
(3, 'daily', '完善个人简历（重点补充实习经历）', 1, '2025-12-15', '2025-12-14 18:00:00', 1, '2025-12-10 10:00:00', '2025-12-14 18:00:00', 0),
(3, 'daily', '练习面试自我介绍（1分钟和3分钟版本）', 2, '2025-12-16', '2025-12-16 11:00:00', 1, '2025-12-16 09:00:00', '2025-12-16 11:00:00', 0),
-- exam（备考任务）：3个任务
(3, 'exam', '学习Java面试常见算法题（冒泡、快排）', 2, '2025-12-22', NULL, 0, '2025-12-11 09:00:00', '2025-12-11 09:00:00', 0),
(3, 'exam', '学习MySQL索引优化知识点及面试题', 1, '2025-12-24', NULL, 0, '2025-12-12 10:00:00', '2025-12-12 10:00:00', 0),
(3, 'exam', '刷Java后端面试题（集合框架部分）', 1, '2025-12-20', '2025-12-19 16:00:00', 1, '2025-12-10 14:00:00', '2025-12-19 16:00:00', 0),
-- plan（规划任务）：2个任务
(3, 'plan', '投递10家互联网公司的后端开发岗位', 1, '2025-12-20', NULL, 0, '2025-12-10 14:00:00', '2025-12-10 14:00:00', 0),
(3, 'plan', '制定一周内的面试学习计划（Java方向）', 2, '2025-12-18', '2025-12-17 15:00:00', 1, '2025-12-11 14:00:00', '2025-12-17 15:00:00', 0),
-- other（其他事项）：1个任务
(3, 'other', '预约公司面试的线上会议室', 3, '2025-12-21', NULL, 0, '2025-12-15 11:00:00', '2025-12-15 11:00:00', 0),

-- ========== user_id=4（考研） ==========
-- daily（日常学习）：2个任务
(4, 'daily', '做考研英语阅读真题2篇并分析错题', 2, '2025-12-17', '2025-12-17 09:00:00', 1, '2025-12-17 07:30:00', '2025-12-17 09:00:00', 0),
(4, 'daily', '背诵考研英语作文模板2篇', 2, '2025-12-18', '2025-12-18 08:30:00', 1, '2025-12-18 07:00:00', '2025-12-18 08:30:00', 0),
-- exam（备考任务）：3个任务
(4, 'exam', '复习考研专业课《操作系统》进程调度章节', 1, '2025-12-26', NULL, 0, '2025-12-12 10:00:00', '2025-12-12 10:00:00', 0),
(4, 'exam', '做考研数学真题（高数部分）一套', 1, '2025-12-24', '2025-12-23 15:00:00', 1, '2025-12-11 09:00:00', '2025-12-23 15:00:00', 0),
(4, 'exam', '复习考研专业课《计算机网络》TCP/IP章节', 1, '2025-12-28', NULL, 0, '2025-12-13 10:00:00', '2025-12-13 10:00:00', 0),
-- plan（规划任务）：2个任务
(4, 'plan', '制定考研专业课最后冲刺复习计划', 2, '2025-12-20', NULL, 0, '2025-12-10 14:00:00', '2025-12-10 14:00:00', 0),
(4, 'plan', '预约考研专业课模拟面试（学长辅导）', 2, '2025-12-25', NULL, 0, '2025-12-12 14:00:00', '2025-12-12 14:00:00', 0),
-- other（其他事项）：1个任务
(4, 'other', '向考上的学长请教复试注意事项', 3, '2025-12-30', NULL, 0, '2025-12-14 16:00:00', '2025-12-14 16:00:00', 0),

-- ========== user_id=5（考公） ==========
-- daily（日常学习）：2个任务
(5, 'daily', '听行测判断推理网课2节', 2, '2025-12-18', '2025-12-18 14:00:00', 1, '2025-12-18 09:00:00', '2025-12-18 14:00:00', 0),
(5, 'daily', '背诵申论时政热点金句20句', 2, '2025-12-19', '2025-12-19 10:00:00', 1, '2025-12-19 08:00:00', '2025-12-19 10:00:00', 0),
-- exam（备考任务）：3个任务
(5, 'exam', '完成申论真题一套（限时3小时）', 1, '2025-12-27', NULL, 0, '2025-12-13 10:00:00', '2025-12-13 10:00:00', 0),
(5, 'exam', '刷行测言语理解模块100道真题', 1, '2025-12-22', '2025-12-21 14:00:00', 1, '2025-12-11 09:00:00', '2025-12-21 14:00:00', 0),
(5, 'exam', '刷行测常识判断模块50道真题（法律部分）', 1, '2025-12-24', NULL, 0, '2025-12-12 10:00:00', '2025-12-12 10:00:00', 0),
-- plan（规划任务）：2个任务
(5, 'plan', '整理考公时政热点（近3个月）', 2, '2025-12-24', NULL, 0, '2025-12-11 15:00:00', '2025-12-11 15:00:00', 0),
(5, 'plan', '报名省考行测线下刷题班', 2, '2025-12-28', NULL, 0, '2025-12-13 14:00:00', '2025-12-13 14:00:00', 0),
-- other（其他事项）：1个任务
(5, 'other', '购买考公行测答题卡和申论答题纸', 3, '2025-12-26', NULL, 0, '2025-12-14 11:00:00', '2025-12-14 11:00:00', 0),

-- ========== user_id=6（就业） ==========
-- daily（日常学习）：2个任务
(6, 'daily', '练习面试自我介绍（1分钟版本）', 2, '2025-12-19', '2025-12-19 11:00:00', 1, '2025-12-19 09:00:00', '2025-12-19 11:00:00', 0),
(6, 'daily', '完善个人简历（添加项目经验细节）', 2, '2025-12-20', '2025-12-20 16:00:00', 1, '2025-12-20 09:00:00', '2025-12-20 16:00:00', 0),
-- exam（备考任务）：3个任务
(6, 'exam', '学习Python数据分析库（Pandas）常用方法', 1, '2025-12-23', NULL, 0, '2025-12-10 10:00:00', '2025-12-10 10:00:00', 0),
(6, 'exam', '学习SQL面试常见查询题（分组、联表）', 1, '2025-12-25', NULL, 0, '2025-12-11 10:00:00', '2025-12-11 10:00:00', 0),
(6, 'exam', '刷产品经理面试题（用户需求分析部分）', 1, '2025-12-22', '2025-12-21 15:00:00', 1, '2025-12-10 14:00:00', '2025-12-21 15:00:00', 0),
-- plan（规划任务）：2个任务
(6, 'plan', '投递8家互联网公司的数据分析岗位', 1, '2025-12-24', NULL, 0, '2025-12-12 14:00:00', '2025-12-12 14:00:00', 0),
(6, 'plan', '制定Python数据分析学习计划（一周）', 2, '2025-12-20', '2025-12-19 15:00:00', 1, '2025-12-11 14:00:00', '2025-12-19 15:00:00', 0),
-- other（其他事项）：1个任务
(6, 'other', '参加校园招聘会（计算机专场）', 3, '2025-12-26', NULL, 0, '2025-12-15 08:00:00', '2025-12-15 08:00:00', 0);
