package com.tdd.service.impl;

import com.tdd.data.Mortage;
import com.tdd.service.MortageService;
import org.springframework.stereotype.Service;

@Service
public class MortageServiceImpl implements MortageService {


    /*
    Duration            < 1000  > 1000
    7 - 45 days	        2.90%	3.40%
    46 - 179 days	    3.90%	4.40%
    180 - upto 1 yr	    4.40%	4.90%
    1 yr - upto 2 yrs	5.00%	5.50%
    2 yrs - upto 3 yrs	5.10%	5.60%
    3 yrs - upto 5 yrs	5.30%	5.80%
    5 - 10 yrs	        5.40%   5.90%
     */
    @Override
    public Mortage calculateMortage(int principal, int duration) {
        // TODO : Send the Correct Mortage, depending on principal and duration
        Mortage mortage = new Mortage(15,115);
        return mortage;
    }
}
