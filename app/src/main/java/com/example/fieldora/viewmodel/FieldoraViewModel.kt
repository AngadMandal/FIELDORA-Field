package com.example.fieldora.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fieldora.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class FieldoraViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow(
        UserProfile(
            userId = "usr_001",
            name = "Rahul Sharma",
            email = "rahul.sharma@fieldora.com",
            role = UserRole.FIELD_EXECUTIVE,
            designation = "Senior Field Executive",
            department = "Sales",
            branchId = "branch_delhi",
            territoryId = "territory_north"
        )
    )
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    private val _isCheckedIn = MutableStateFlow(false)
    val isCheckedIn: StateFlow<Boolean> = _isCheckedIn.asStateFlow()

    private val _checkInTime = MutableStateFlow("")
    val checkInTime: StateFlow<String> = _checkInTime.asStateFlow()

    private val _customers = MutableStateFlow(
        listOf(
            Customer("cust_01", "Apex Retail Store", "+91 98111 22334", "contact@apexretail.com", "Connaught Place, New Delhi", "Retailer", "North Delhi", CustomerStatus.ACTIVE, "2026-09-11", "2026-09-15", "High demand for POS hardware."),
            Customer("cust_02", "Metro Supermarket", "+91 98222 33445", "purchasing@metromarket.in", "Lajpat Nagar, New Delhi", "Supermarket", "South Delhi", CustomerStatus.ACTIVE, "2026-09-10", "2026-09-16", "Interested in bulk restocking."),
            Customer("cust_03", "Sunrise Pharma", "+91 98333 44556", "info@sunrisepharma.com", "Karol Bagh, New Delhi", "Pharmacy", "Central Delhi", CustomerStatus.PROSPECT, "2026-09-08", "2026-09-14", "Initial pitch completed.")
        )
    )
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()

    private val _leads = MutableStateFlow(
        listOf(
            Lead("lead_01", "Apex Retail Store", "Enterprise POS", 75000.0, LeadStage.PROPOSAL, "Rahul Sharma", 70, "2026-09-16"),
            Lead("lead_02", "Metro Supermarket", "Inventory Cloud", 120000.0, LeadStage.NEGOTIATION, "Priya Singh", 85, "2026-09-15"),
            Lead("lead_03", "Sunrise Pharma", "Analytics Pro", 45000.0, LeadStage.INTERESTED, "Amit Kumar", 50, "2026-09-18")
        )
    )
    val leads: StateFlow<List<Lead>> = _leads.asStateFlow()

    private val _visits = MutableStateFlow(
        listOf(
            Visit("vis_01", "Apex Retail Store", "Rahul Sharma", "Monthly Stock Review", "09:45 AM", VisitOutcome.SUCCESSFUL, "Ordered 3 units of POS terminals.", 28.6139, 77.2090),
            Visit("vis_02", "Metro Supermarket", "Rahul Sharma", "Pricing Negotiation", "11:30 AM", VisitOutcome.INTERESTED, "Requested revised quotation.", 28.5355, 77.3910)
        )
    )
    val visits: StateFlow<List<Visit>> = _visits.asStateFlow()

    private val _tasks = MutableStateFlow(
        listOf(
            TaskItem("task_01", "Verify Store Display", "Check promotional banners at Apex Retail", "Rahul Sharma", "2026-09-15", TaskPriority.HIGH, TaskStatus.IN_PROGRESS),
            TaskItem("task_02", "Collect Pending Invoice", "Collect INR 24,000 from Metro Supermarket", "Rahul Sharma", "2026-09-14", TaskPriority.URGENT, TaskStatus.PENDING),
            TaskItem("task_03", "Retailer Survey", "Complete Q3 satisfaction survey", "Rahul Sharma", "2026-09-16", TaskPriority.MEDIUM, TaskStatus.COMPLETED)
        )
    )
    val tasks: StateFlow<List<TaskItem>> = _tasks.asStateFlow()

    private val _campaigns = MutableStateFlow(
        listOf(
            Campaign("cmp_01", "Diwali Retail Surge", "Retail Promotion", "2026-09-01", "2026-09-30", 120, 52, 50000.0, "Active"),
            Campaign("cmp_02", "New POS Awareness", "Brand Awareness", "2026-09-05", "2026-09-25", 80, 30, 30000.0, "Active")
        )
    )
    val campaigns: StateFlow<List<Campaign>> = _campaigns.asStateFlow()

    private val _expenses = MutableStateFlow(
        listOf(
            Expense("exp_01", "Rahul Sharma", "Fuel", 450.0, "2026-09-11", "Client visit fuel for North Delhi route", ExpenseStatus.APPROVED),
            Expense("exp_02", "Rahul Sharma", "Food", 280.0, "2026-09-10", "Business lunch with retailer", ExpenseStatus.SUBMITTED)
        )
    )
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            NotificationItem("notif_01", "New Lead Assigned", "You have been assigned lead Metro Supermarket", "10:30 AM", false),
            NotificationItem("notif_02", "Expense Approved", "Fuel expense of INR 450 was approved", "Yesterday", true),
            NotificationItem("notif_03", "Campaign Target Updated", "Diwali Retail Surge target increased", "2 days ago", true)
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _auditLogs = MutableStateFlow(
        listOf(
            AuditLogEntry("aud_01", "Admin User", "User Role Updated", "Employees", "2026-09-12 09:00"),
            AuditLogEntry("aud_02", "Super Admin", "System Backup Created", "Backup", "2026-09-11 23:59")
        )
    )
    val auditLogs: StateFlow<List<AuditLogEntry>> = _auditLogs.asStateFlow()

    // AI Chat state
    private val _aiMessages = MutableStateFlow(
        listOf(
            Pair("ai", "Hello Rahul! I am FIELDORA AI, your field operations intelligence assistant. How can I assist you today? You can ask me about visits, overdue leads, or daily reports.")
        )
    )
    val aiMessages: StateFlow<List<Pair<String, String>>> = _aiMessages.asStateFlow()

    private val _employeeLocations = MutableStateFlow(
        listOf(
            EmployeeLocationState("emp_01", "Rahul Sharma", 28.6139, 77.2090, "10:45 AM", LocationStateStatus.LIVE, "North Zone", "Doctor Clinic Visit"),
            EmployeeLocationState("emp_02", "Priya Singh", 28.5355, 77.3910, "10:30 AM", LocationStateStatus.LIVE, "South Zone", "Pharmacy Store Check"),
            EmployeeLocationState("emp_03", "Amit Kumar", 28.7041, 77.1025, "09:15 AM", LocationStateStatus.RECENT, "Central Zone", "Retailer Pitch")
        )
    )
    val employeeLocations: StateFlow<List<EmployeeLocationState>> = _employeeLocations.asStateFlow()

    private val _routeHistory = MutableStateFlow(
        listOf(
            LocationRoutePoint("09:00 AM", "Office HQ - Check In", 28.6100, 77.2000, "Check-in"),
            LocationRoutePoint("09:30 AM", "Apex Retail Store", 28.6139, 77.2090, "Customer Visit"),
            LocationRoutePoint("10:15 AM", "Metro Supermarket", 28.5355, 77.3910, "Doctor Clinic Visit"),
            LocationRoutePoint("11:00 AM", "Market Area Promotion", 28.7041, 77.1025, "Campaign")
        )
    )
    val routeHistory: StateFlow<List<LocationRoutePoint>> = _routeHistory.asStateFlow()

    private val _geofences = MutableStateFlow(
        listOf(
            GeofenceZone("geo_01", "HQ Branch Zone", "Branch", 28.6100, 77.2000, 200, "Active"),
            GeofenceZone("geo_02", "North Zone Clinic Area", "Clinic", 28.6139, 77.2090, 100, "Active"),
            GeofenceZone("geo_03", "South Retail Hub", "Store", 28.5355, 77.3910, 150, "Active")
        )
    )
    val geofences: StateFlow<List<GeofenceZone>> = _geofences.asStateFlow()

    fun switchRole(role: UserRole) {
        val name = when (role) {
            UserRole.SUPER_ADMIN -> "Vikram Aditya (Super Admin)"
            UserRole.ADMIN -> "Ananya Roy (Admin)"
            UserRole.MANAGER -> "Rajesh Verma (Sales Manager)"
            UserRole.FIELD_EXECUTIVE -> "Rahul Sharma (Field Executive)"
            UserRole.VIEWER -> "Audit Viewer"
        }
        _currentUser.value = _currentUser.value.copy(role = role, name = name)
    }

    fun toggleCheckIn() {
        val current = _isCheckedIn.value
        if (!current) {
            val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            _checkInTime.value = time
            _isCheckedIn.value = true
        } else {
            _isCheckedIn.value = false
            _checkInTime.value = ""
        }
    }

    fun addCustomer(name: String, phone: String, email: String, address: String, type: String) {
        val newCust = Customer(
            customerId = "cust_${System.currentTimeMillis()}",
            name = name,
            phone = phone,
            email = email,
            address = address,
            customerType = type,
            territory = "North Delhi",
            status = CustomerStatus.ACTIVE,
            lastVisit = "Today",
            nextFollowUp = "In 3 days",
            notes = "Newly added via mobile app"
        )
        _customers.value = listOf(newCust) + _customers.value
    }

    fun addLead(customerName: String, product: String, value: Double) {
        val newLead = Lead(
            leadId = "lead_${System.currentTimeMillis()}",
            customerName = customerName,
            product = product,
            expectedValue = value,
            stage = LeadStage.NEW,
            owner = _currentUser.value.name,
            probability = 50,
            nextFollowUp = "Tomorrow"
        )
        _leads.value = listOf(newLead) + _leads.value
    }

    fun addVisit(customerName: String, purpose: String, outcome: VisitOutcome, notes: String) {
        val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val newVisit = Visit(
            visitId = "vis_${System.currentTimeMillis()}",
            customerName = customerName,
            employeeName = _currentUser.value.name,
            purpose = purpose,
            timestamp = time,
            outcome = outcome,
            notes = notes
        )
        _visits.value = listOf(newVisit) + _visits.value
    }

    fun addExpense(category: String, amount: Double, desc: String) {
        val newExp = Expense(
            expenseId = "exp_${System.currentTimeMillis()}",
            employeeName = _currentUser.value.name,
            category = category,
            amount = amount,
            date = "Today",
            description = desc,
            status = ExpenseStatus.SUBMITTED
        )
        _expenses.value = listOf(newExp) + _expenses.value
    }

    fun sendAiQuery(query: String) {
        val currentList = _aiMessages.value.toMutableList()
        currentList.add(Pair("user", query))

        val answer = when {
            query.contains("check", ignoreCase = true) -> "Today, 12 out of 15 field executives have checked in successfully. Average check-in time: 09:12 AM."
            query.contains("lead", ignoreCase = true) -> "You currently have 3 high-probability leads pending follow-up with expected value totaling INR 240,000."
            query.contains("territory", ignoreCase = true) -> "North Delhi territory generated the highest lead conversion rate (34%) this month."
            query.contains("report", ignoreCase = true) -> "Generated today's field activity report: 24 visits logged, 8 new leads created, 4 task checklists completed."
            else -> "Based on your permissions and real-time Firestore analytics, FIELDORA operations are running smoothly with 92% target achievement across active campaigns."
        }

        currentList.add(Pair("ai", answer))
        _aiMessages.value = currentList
    }
}
