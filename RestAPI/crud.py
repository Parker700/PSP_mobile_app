from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from models import User

# Создание нового пользователя
async def create_user(db: AsyncSession, username: str, full_name: str, email: str):
    new_user = User(username=username, full_name=full_name, email=email)
    db.add(new_user)
    await db.commit()
    await db.refresh(new_user)
    return new_user

# Получение пользователя по имени
async def get_user_by_username(db: AsyncSession, username: str):
    result = await db.execute(select(User).filter(User.username == username))
    return result.scalars().first()

# Получение всех пользователей
async def get_all_users(db: AsyncSession):
    result = await db.execute(select(User))
    return result.scalars().all()