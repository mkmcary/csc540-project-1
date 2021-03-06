-- Brands
INSERT INTO Brands (bname, baddress, username, pass, joinDate) values ('Brand X', '503 Rolling Creek Dr Austin, AR', 'brandx', 'pass', TO_DATE('2021-04-01', 'yyyy-mm-dd'));
INSERT INTO Brands (bname, baddress, username, pass, joinDate) values ('Brand Y', '939 Orange Ave Coronado, CA', 'brandy', 'pass', TO_DATE('2021-03-25', 'yyyy-mm-dd'));
INSERT INTO Brands (bname, baddress, username, pass, joinDate) values ('Brand Z', '20 Roszel Rd Princeton, NJ', 'brandz', 'pass', TO_DATE('2021-05-08', 'yyyy-mm-dd'));

-- Loyalty Programs
INSERT INTO LoyaltyPrograms (pName, isTiered, bId) values ('SportGoods', 'Y', 1);
INSERT INTO LoyaltyPrograms (pName, isTiered, bId) values ('MegaCenter', 'Y', 2);
INSERT INTO LoyaltyPrograms (pName, isTiered, bId) values ('TechSups', 'N', 3);

-- Tiers
INSERT INTO Tiers (pId, tnum, tname, multiplier, threshold) values (1, 0, 'Bronze', 1, 0);
INSERT INTO Tiers (pId, tnum, tname, multiplier, threshold) values (1, 1, 'Silver', 2, 170);
INSERT INTO Tiers (pId, tnum, tname, multiplier, threshold) values (1, 2, 'Gold', 3, 270);

INSERT INTO Tiers (pId, tnum, tname, multiplier, threshold) values (2, 0, 'Special', 1, 0);
INSERT INTO Tiers (pId, tnum, tname, multiplier, threshold) values (2, 1, 'Premium', 2, 210);

-- Customers
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Peter Parker', '8636368778', '20 Ingram Street, NY', 'peterparker', 'pass');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Steve Rogers', '8972468552', '569 Leaman Place, NY', 'steverogers', 'pass');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Diana Prince', '8547963210', '1700 Broadway St, NY', 'dianaprince', 'pass');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Billy Batson', '8974562583', '5015 Broad St, Philadelphia, PA', 'billybatson', 'pass');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Tony Stark', '8731596464', '10880 Malibu Point, CA', 'tonystark', 'pass');

-- Wallet Partipation
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (1, 1, 0, 80, 0);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (1, 2, 0, 210, 1);

INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (2, 1, 0, 70, 0);

INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (3, 2, 20, 200, 0);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (3, 3, 40, 40, NULL);

INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (5, 1, 20, 170, 1);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (5, 2, 40, 160, 0);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (5, 3, 50, 50, NULL);

-- Activity Categories
INSERT INTO ActivityCategories (acId, acName) values ('A01', 'Purchase');
INSERT INTO ActivityCategories (acId, acName) values ('A02', 'Write a Review');
INSERT INTO ActivityCategories (acId, acName) values ('A03', 'Refer a Friend');

-- Program Activities
INSERT INTO ProgramActivities (pId, acId) values (1, 'A01');
INSERT INTO ProgramActivities (pId, acId) values (1, 'A02');

INSERT INTO ProgramActivities (pId, acId) values (2, 'A01');
INSERT INTO ProgramActivities (pId, acId) values (2, 'A03');

INSERT INTO ProgramActivities (pId, acId) values (3, 'A03');

-- Rewards
INSERT INTO Rewards (rId, rName) values ('R01', 'Gift Card');
INSERT INTO Rewards (rId, rName) values ('R02', 'Free Product');

-- Program Rewards
INSERT INTO ProgramRewards (pId, rId) values (1, 'R01');
INSERT INTO ProgramRewards (pId, rId) values (1, 'R02');

INSERT INTO ProgramRewards (pId, rId) values (2, 'R01');
INSERT INTO ProgramRewards (pId, rId) values (2, 'R02');

INSERT INTO ProgramRewards (pId, rId) values (3, 'R01');

-- Reward Earning Rules
INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (1, 1, 'RE01', 15, 'A01');
INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (1, 1, 'RE02', 10, 'A02');

INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (2, 1, 'RE01', 40, 'A01');
INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (2, 1, 'RE02', 30, 'A03');

INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (3, 1, 'RE01', 10, 'A03');

-- Reward Redeeming Rules
INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity, gcVal, gcExp) values (1, 1, 'RR01', 80, 'R01', 40, 10, TO_DATE('2022-12-31', 'yyyy-mm-dd'));
INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity) values (1, 1, 'RR02', 70, 'R02', 25);

INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity, gcVal, gcExp) values (2, 1, 'RR01', 120, 'R01', 30, 10, TO_DATE('2022-12-31', 'yyyy-mm-dd'));
INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity) values (2, 1, 'RR02', 90, 'R02', 50);

INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity, gcVal, gcExp) values (3, 1, 'RR01', 100, 'R01', 25, 10, TO_DATE('2022-12-31', 'yyyy-mm-dd'));

-- Activity Instances
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-06-10', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE01', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-06-18', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE02', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-09', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE01', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-15', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE01', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-03', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE02', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-18', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE02', 1);

INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-07-02', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE01', 2);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-07-08', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE01', 2);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-05', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE02', 2);

INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-07-30', 'yyyy-mm-dd'), 'Description', 3, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-01', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-10', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-09-02', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-01', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE02', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-16', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE02', 3);

INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-10', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-09-16', 'yyyy-mm-dd'), 'Description', 3, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-09-30', 'yyyy-mm-dd'), 'Description', 3, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-09-30', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE02', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-10', 'yyyy-mm-dd'), 'Description', 2, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-15', 'yyyy-mm-dd'), 'Description', 1, 1, 'RE02', 5);

-- Reward Instances
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-07-02', 'yyyy-mm-dd'), 1, 1, 'RR01', 1);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-25', 'yyyy-mm-dd'), 2, 1, 'RR01', 1);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-20', 'yyyy-mm-dd'), 2, 1, 'RR02', 1);

INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-09-01', 'yyyy-mm-dd'), 1, 1, 'RR02', 2);

INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-08-26', 'yyyy-mm-dd'), 2, 1, 'RR02', 3);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-18', 'yyyy-mm-dd'), 2, 1, 'RR02', 3);

INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-11', 'yyyy-mm-dd'), 2, 1, 'RR01', 5);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-11', 'yyyy-mm-dd'), 1, 1, 'RR01', 5);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (TO_DATE('2021-10-17', 'yyyy-mm-dd'), 1, 1, 'RR02', 5);

-- Gift Cards
INSERT INTO GiftCards (pId, wId, cardValue, expiryDate) values (1, 1, 10, TO_DATE('2022-12-31', 'yyyy-mm-dd'));
INSERT INTO GiftCards (pId, wId, cardValue, expiryDate) values (2, 1, 10, TO_DATE('2022-12-31', 'yyyy-mm-dd'));

INSERT INTO GiftCards (pId, wId, cardValue, expiryDate) values (2, 5, 10, TO_DATE('2022-12-31', 'yyyy-mm-dd'));
INSERT INTO GiftCards (pId, wId, cardValue, expiryDate) values (1, 5, 10, TO_DATE('2022-12-31', 'yyyy-mm-dd'));

-- Admin
INSERT INTO Admins (username, pass) values ('admin', 'pass');
