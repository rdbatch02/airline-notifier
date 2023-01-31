package rss.sources

import rss.Record

interface Source {
    suspend fun getRecords(): List<Record>
}