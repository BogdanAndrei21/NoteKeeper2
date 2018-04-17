package com.example.bogdanandrei.notekeeper;

public enum TypeEnum
{
    School(1),
    Friends(2),
    Money(3),
    Hangout(4),
    HomeStuffs(5),
    Others(6);

    private int rank;

    TypeEnum(int rank)
    {
        this.rank = rank;
    }

}
