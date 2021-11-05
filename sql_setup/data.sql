-- Loyalty Programs
INSERT INTO LoyaltyPrograms (pName, pCode, isTiered) values ('SportGoods', 'TLP01', true);
INSERT INTO LoyaltyPrograms (pName, pCode, isTiered) values ('MegaCenter', 'TLP02', true);
INSERT INTO LoyaltyPrograms (pName, pCode, isTiered) values ('TechSups', 'RLP01', false);

-- Tiers
INSERT INTO Tiers (pId, tnum, tname, multipler, threshold) values (1, 0, 'Bronze', 1, 0);
INSERT INTO Tiers (pId, tnum, tname, multipler, threshold) values (1, 1, 'Silver', 2, 170);
INSERT INTO Tiers (pId, tnum, tname, multipler, threshold) values (1, 2, 'Gold', 3, 270);

INSERT INTO Tiers (pId, tnum, tname, multipler, threshold) values (2, 0, 'Special', 1, 0);
INSERT INTO Tiers (pId, tnum, tname, multipler, threshold) values (2, 1, 'Premium', 2, 210);

-- Brands
INSERT INTO Brands (bname, baddress, username, pass, joinDate, pId) values ('Brand X', '503 Rolling Creek Dr Austin, AR', 'brandx', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484', '2021-04-01', 1);
INSERT INTO Brands (bname, baddress, username, pass, joinDate, pId) values ('Brand Y', '939 Orange Ave Coronado, CA', 'brandy', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484', '2021-03-25', 2);
INSERT INTO Brands (bname, baddress, username, pass, joinDate, pId) values ('Brand Z', '20 Roszel Rd Princeton, NJ', 'brandz', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484', '2021-05-08', 3);

-- Customers
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Peter Parker', '8636368778', '20 Ingram Street, NY', 'peterparker', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Steve Rogers', '8972468552', '569 Leaman Place, NY', 'steverogers', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Diana Prince', '8547963210', '1700 Broadway St, NY', 'dianaprince', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Billy Batson', '8974562583', '5015 Broad St, Philadelphia, PA', 'billybatson', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484');
INSERT INTO Customers (cname, phoneNumber, caddress, username, pass) values ('Tony Stark', '8731596464', '10880 Malibu Point, CA', 'tonystark', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484');

-- Wallets
INSERT INTO Wallets;
INSERT INTO Wallets;
INSERT INTO Wallets;
INSERT INTO Wallets;
INSERT INTO Wallets;

-- CustomerWallets
INSERT INTO CustomerWallets (cId, wId) values (1, 1);
INSERT INTO CustomerWallets (cId, wId) values (2, 2);
INSERT INTO CustomerWallets (cId, wId) values (3, 3);
INSERT INTO CustomerWallets (cId, wId) values (4, 4);
INSERT INTO CustomerWallets (cId, wId) values (5, 5);

-- Wallet Partipation
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (1, 1, 0, 80, 0);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (1, 2, 0, 210, 1);

INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (2, 1, 0, 70, 0);

INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (3, 2, 20, 200, 0);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (3, 3, 40, 40, null);

INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (5, 1, 20, 170, 1);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (5, 2, 40, 160, 0);
INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (5, 3, 50, 50, null);

-- Activity Categories
INSERT INTO ActivityCategories (acId, acName) values ('A01', 'Purchase');
INSERT INTO ActivityCategories (acId, acName) values ('A02', 'Write a Review');
INSERT INTO ActivityCategories (acId, acName) values ('A03', 'Refer a Friend');

-- Rewards
INSERT INTO Rewards (rId, rName) values ('R01', 'Gift Card');
INSERT INTO Rewards (rId, rName) values ('R02', 'Free Product');

-- Reward Earning Rules
INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (1, 1, 'RE01', 15, 'A01');
INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (1, 1, 'RE02', 10, 'A02');

INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (2, 1, 'RE01', 40, 'A01');
INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (2, 1, 'RE02', 30, 'A03');

INSERT INTO RewardEarningRules (pId, ruleVersion, ruleCode, points, acId) values (3, 1, 'RE01', 10, 'A03');

-- Reward Redeeming Rules
INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity) values (1, 1, 'RR01', 80, 'R01', 40);
INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity) values (1, 1, 'RR02', 70, 'R02', 25);

INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity) values (2, 1, 'RR01', 120, 'R01', 30);
INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity) values (2, 1, 'RR02', 90, 'R02', 50);

INSERT INTO RewardRedeemingRules (pId, ruleVersion, ruleCode, points, rId, quantity) values (3, 1, 'RR01', 100, 'R01', 25);

-- Activity Instances
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-06-10', 'Description', 1, 1, 'RE01', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-06-18', 'Description', 1, 1, 'RE02', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-08-09', 'Description', 2, 1, 'RE01', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-08-15', 'Description', 2, 1, 'RE01', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-10-03', 'Description', 2, 1, 'RE02', 1);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-10-18', 'Description', 2, 1, 'RE02', 1);

INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-07-02', 'Description', 1, 1, 'RE01', 2);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-07-08', 'Description', 1, 1, 'RE01', 2);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-08-05', 'Description', 1, 1, 'RE02', 2);

INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-07-30', 'Description', 3, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-08-01', 'Description', 2, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-08-10', 'Description', 2, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-09-02', 'Description', 2, 1, 'RE01', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-10-01', 'Description', 2, 1, 'RE02', 3);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-10-16', 'Description', 2, 1, 'RE02', 3);

INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-08-10', 'Description', 1, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-09-16', 'Description', 3, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-09-30', 'Description', 3, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-09-30', 'Description', 1, 1, 'RE02', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-10-10', 'Description', 2, 1, 'RE01', 5);
INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values ('2021-10-15', 'Description', 1, 1, 'RE02', 5);

-- Reward Instances
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-07-02', 1, 1, 'RR01', 1);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-08-25', 2, 1, 'RR01', 1);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-10-20', 2, 1, 'RR02', 1);

INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-09-01', 1, 1, 'RR02', 2);

INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-08-26', 2, 1, 'RR02', 3);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-10-18', 2, 1, 'RR02', 3);

INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-10-11', 2, 1, 'RR01', 5);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-10-11', 1, 1, 'RR01', 5);
INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values ('2021-10-17', 1, 1, 'RR02', 5);

-- Gift Cards
INSERT INTO GiftCards (pId, wId, cardValue) values (1, 1, 40);
INSERT INTO GiftCards (pId, wId, cardValue) values (2, 1, 30);

INSERT INTO GiftCards (pId, wId, cardValue) values (2, 5, 30);
INSERT INTO GiftCards (pId, wId, cardValue) values (1, 5, 40);

-- Admin
INSERT INTO Admins (username, pass) values ('admin', 'c0067d4af4e87f00dbac63b6156828237059172d1bbeac67427345d6a9fda484');
