#include <iostream>
#include <vector>
#include <set>
#include <cstdlib>
#include <ctime>
#include <fstream>

using namespace std;

string USERNAME = "";
string PASSWORD = "";
const int OTP = 2311;

string generateUsername()
{
    return "user" + to_string(1000 + rand() % 9000);
}

string generatePassword()
{
    string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#";
    string pass = "";

    for (int i = 0; i < 8; i++)
        pass += chars[rand() % chars.size()];

    return pass;
}

class login
{
protected:
    string username, password;

public:
    bool verification()
    {
        int attempts = 0;

        while (attempts < 3)
        {
            cout << "Enter username: "<<endl;
            cin >> username;

            cout << "Enter password: "<<endl;
            cin >> password;

            if (username == USERNAME && password == PASSWORD)
            {
                cout << "Login successful\n"<<endl;
                return true;
            }
            else
            {
                cout << "Invalid credentials\n"<<endl;
                attempts++;
            }
        }

        cout << "Account locked!\n"<<endl;
        return false;
    }
};

class reg : public login
{
public:
    void set_reg()
    {
        int otp;

        cout << "Enter OTP: "<<endl;
        cin >> otp;

        if (otp != OTP)
        {
            cout << "Invalid OTP. Cannot continue.\n"<<endl;
            return;
        }

        USERNAME = generateUsername();
        PASSWORD = generatePassword();

        cout << "\nRegistration Successful!\n"<<endl;
        cout << "Username: " << USERNAME << endl;
        cout << "Password: " << PASSWORD << endl;
    }
};

class forgotpass : public login
{
public:
    void forgot_val()
    {
        string user;
        int code;

        cout << "Enter username: "<<endl;
        cin >> user;

        if (user != USERNAME)
        {
            cout << "Username not found!\n"<<endl;
            return;
        }

        cout << "Enter OTP: "<<endl;
        cin >> code;

        if (code != OTP)
        {
            cout << "Wrong OTP. Cannot continue.\n"<<endl;
            return;
        }

        PASSWORD = generatePassword();
        cout << "New Password: " << PASSWORD << endl;
    }
};

class Candidate
{
private:
    string name, party;
    int id, votes;

public:
    Candidate() { votes = 0; }

    void set_values(string n, string p, int i)
    {
        name = n;
        party = p;
        id = i;
        votes = 0;
    }

    void display() const
    {
        cout << "ID: " << id << endl;
        cout << "Name: " << name << endl;
        cout << "Party: " << party << endl;
        cout << "Votes: " << votes << endl;
        cout << "----------------------\n";
    }

    int getID() { return id; }

    void addVote() { votes++; }
};

class VotingSystem
{
private:
    vector<Candidate> c;
    set<int> votedIDs;
    set<int> candidateIDs;

public:
    bool isLoggedIn = false;

  void saveVoteToFile(int voterID, int candidateID)
{
    ofstream file("votes.csv", ios::app);

    if (file.is_open())
    {
        file << voterID << "," << candidateID << endl;  // ✅ added this line
        file.close();
    }
    else
    {
        cout << "Error opening file!\n";
    }
}

    void inputCandidates()
    {
        int n;
        cout << "Enter number of candidates: "<<endl;
        cin >> n;

        for (int i = 0; i < n; i++)
        {
            string name, party;
            int id;

            cin.ignore();

            cout << "\nEnter candidate name: "<<endl;
            getline(cin, name);

            cout << "Enter party: "<<endl;
            getline(cin, party);

            cout << "Enter ID: "<<endl;
            cin >> id;

            if (candidateIDs.count(id))
            {
                cout << "Duplicate ID! Try again.\n"<<endl;
                i--;
                continue;
            }

            candidateIDs.insert(id);

            Candidate temp;
            temp.set_values(name, party, id);
            c.push_back(temp);
        }
    }

    void displayCandidates()
    {
        cout << "\n===== Candidates =====\n";
        for (Candidate &cand : c)
            cand.display();
    }

    void vote()
    {
        if (!isLoggedIn)
        {
            cout << "Login first!\n"<<endl;
            return;
        }

        int voterID;
        cout << "Enter your Voter ID: "<<endl;
        cin >> voterID;

        if (votedIDs.count(voterID))
        {
            cout << "You have already voted!\n"<<endl;
            return;
        }

        int choice;
        cout << "Enter Candidate ID to vote: "<<endl;
        cin >> choice;

        bool found = false;

        for (Candidate &cand : c)
        {
            if (cand.getID() == choice)
            {
                cand.addVote();
                votedIDs.insert(voterID);

                saveVoteToFile(voterID, choice);

                cout << "Vote cast successfully!\n"<<endl;
                found = true;
                break;
            }
        }

        if (!found)
        {
            cout << "Invalid Candidate ID\n"<<endl;
        }
    }
};

int main()
{
    srand(time(0));

    login obj;
    reg r;
    forgotpass fp;
    VotingSystem vs;

    int choice;

    do
    {
        cout<<"==========Welcome to the Election Commission of India==============\n"<<endl;
        cout<<"Choose an option from the dropdown:"<<endl;


        cout << "\n===== MENU =====\n"<<endl;
        cout << "1. Register\n"<<endl;
        cout << "2. Login\n"<<endl;
        cout << "3. Forgot Password\n"<<endl;
        cout << "4. Add Candidates\n"<<endl;
        cout << "5. Display Candidates\n"<<endl;
        cout << "6. Vote\n"<<endl;
        cout << "7. Exit\n"<<endl;
        cout << "Enter option:\n "<<endl;
        cin >> choice;

        switch (choice)
        {
            case 1: r.set_reg(); break;
            case 2: if (obj.verification()) vs.isLoggedIn = true; break;
            case 3: fp.forgot_val(); break;
            case 4: vs.inputCandidates(); break;
            case 5: vs.displayCandidates(); break;
            case 6: vs.vote(); break;
            case 7: cout << "Exiting...\n"<<endl; break;
            default: cout << "Invalid choice\n"<<endl;
        }

    } while (choice != 7);

    return 0;
}
