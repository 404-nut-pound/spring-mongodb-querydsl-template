db = db.getSiblingDB('temp');

db.createUser({
  user: 'temp',
  pwd: 'temp-password',
  roles: [{ role: 'dbOwner', db: 'temp' }],
});

db.createCollection('aa_user');

db.aa_user.insert({
  userId: 'admin',
  userName: '관리자',
  email: 'huiseongkim91@gmail.com',
  password: 'password',
  createdBy: 'system',
  createdAt: ISODate(),
  updatedBy: 'system',
  updatedAt: ISODate(),
});

// auto-index-creation 옵션이 true로 설정돼있으면 인덱스를 지금 생성하지 않아야 함
// db.aa_user.createIndex({ userId: 1 }, { unique: true });
// db.aa_user.createIndex({ email: 1 }, { unique: true });
