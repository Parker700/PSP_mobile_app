from sqlalchemy import Column, Integer, String, Boolean
from database import Base

class User(Base):
    __tablename__ = "users" #table name

    # userID, username,
    id = Column(Integer, primary_key=True, autoincrement=True, index=True)
    username = Column(String, unique=True, nullable=False)  # Уникальное имя пользователя
    full_name = Column(String, nullable=False)  # Полное имя
    email = Column(String, unique=True, nullable=False)  # Email (уникальный)
    is_active = Column(Boolean, default=True)  # Статус (активен или нет)