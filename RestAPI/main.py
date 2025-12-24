from fastapi import FastAPI, Depends
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.sql import text
from sqlalchemy.ext.asyncio import create_async_engine
from sqlalchemy.orm import sessionmaker
from database import get_db, engine
from models import Base
from crud import create_user, get_user_by_username, get_all_users

app = FastAPI()

@app.on_event("startup")
async def startup_event():
    #create table if not exist
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)

@app.get("/")
async def test_connection(db: AsyncSession = Depends(get_db)):
    try:
        result = await db.execute(text("SELECT 1"))
        return {"message": "Baizakov database connection successful"}
    except Exception as e:
        return {"error": str(e)}

@app.post("/users/")
async def add_user(username: str, full_name: str, email: str, db: AsyncSession = Depends(get_db)):
    return await create_user(db, username, full_name, email)

@app.get("/users/")
async def list_users(db: AsyncSession = Depends(get_db)):
    return await get_all_users(db)

@app.get("/users/{username}")
async def get_user(username: str, db: AsyncSession = Depends(get_db)):
    user = await get_user_by_username(db, username)
    if not user:
        return {"error": "User not found"}
    return user
