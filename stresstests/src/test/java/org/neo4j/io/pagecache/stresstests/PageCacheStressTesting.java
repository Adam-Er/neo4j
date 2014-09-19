/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.io.pagecache.stresstests;

import static java.lang.Integer.parseInt;
import static java.lang.System.getenv;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.neo4j.io.pagecache.stress.Conditions.timePeriod;

import org.junit.Test;
import org.neo4j.io.pagecache.stress.PageCacheStressTest;

/**
 * Notice the class name: this is _not_ going to be run as part of the main build.
 */
public class PageCacheStressTesting
{
    @Test
    public void shouldBehaveCorrectlyUnderStress() throws Exception
    {
        int durationInMinutes = parseInt( fromEnvironmentOrDefault( "PAGE_CACHE_STRESS_DURATION", "1" ) );
        int numberOfPages = parseInt( fromEnvironmentOrDefault( "PAGE_CACHE_STRESS_NUMBER_OF_PAGES", "10000" ) );
        int recordsPerPage = parseInt( fromEnvironmentOrDefault( "PAGE_CACHE_STRESS_RECORDS_PER_PAGE", "113" ) );
        int numberOfThreads = parseInt( fromEnvironmentOrDefault( "PAGE_CACHE_STRESS_NUMBER_OF_THREADS", "8" ) );
        int cachePagePadding = parseInt( fromEnvironmentOrDefault( "PAGE_CACHE_STRESS_CACHE_PAGE_PADDING", "56" ) );
        int numberOfCachePages = parseInt( fromEnvironmentOrDefault( "PAGE_CACHE_STRESS_NUMBER_OF_CACHE_PAGES", "1000" ) );

        PageCacheStressTest runner = new PageCacheStressTest.Builder()
                .with( timePeriod( durationInMinutes, MINUTES ) )
                .withNumberOfPages( numberOfPages )
                .withRecordsPerPage( recordsPerPage )
                .withNumberOfThreads( numberOfThreads )
                .withCachePagePadding( cachePagePadding )
                .withNumberOfCachePages( numberOfCachePages )
                .build();

        runner.run();
    }

    private static String fromEnvironmentOrDefault( String environmentVariableName, String defaultValue )
    {
        String environmentVariableValue = getenv( environmentVariableName );

        if ( environmentVariableValue == null )
        {
            return defaultValue;
        }

        return environmentVariableValue;
    }
}
