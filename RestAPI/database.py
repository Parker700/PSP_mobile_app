from sqlalchemy.ext.asyncio import create_async_engine, AsyncSession
from sqlalchemy.orm import sessionmaker, declarative_base
from config import settings

# Создаем базу для моделей
Base = declarative_base()

# Async engine
engine = create_async_engine(
    url=settings.DATABASE_URL_asyncpg, 
    echo=True,
    future=True, 
    #pool_size=5,
    #max_overflow=10
)

# Создаем сессию для выполнения запросов к базе данных
async_session = sessionmaker(bind=engine, class_=AsyncSession, expire_on_commit=False)

# Генератор сессий для FastAPI зависимостей
async def get_db():
    async with async_session() as session:
        yield session
